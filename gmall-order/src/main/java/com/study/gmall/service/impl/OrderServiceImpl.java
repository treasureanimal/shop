package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.Resp;
import com.study.core.exception.OrderException;
import com.study.gmall.cart.pojo.Cart;
import com.study.gmall.cart.pojo.UserInfo;
import com.study.gmall.feign.*;
import com.study.gmall.interceptors.LonginInterceptors;
import com.study.gmall.oms.entity.OrderEntity;
import com.study.gmall.oms.vo.OrderConfirmVO;
import com.study.gmall.oms.vo.OrderItemVO;
import com.study.gmall.oms.vo.OrderSubmitVO;
import com.study.gmall.pms.entity.SkuInfoEntity;
import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.study.gmall.service.OrderService;
import com.study.gmall.ums.entity.MemberEntity;
import com.study.gmall.ums.entity.MemberReceiveAddressEntity;
import com.study.gmall.wms.entity.WareSkuEntity;
import com.study.gmall.wms.vo.SkuLockVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private GmallPmsClientApi pmsClientApi;
    @Autowired
    private GmallWmsClientApi wmsClientApi;
    @Autowired
    private GmallCartClientApi cartClientApi;
    @Autowired
    private GmallSmsClientApi smsClientApi;
    @Autowired
    private GmallUmsClientApi umsClientApi;
    @Autowired
    private GmalOmsClientApi omsClientApi;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static final String TOKEN_PREFIX = "order:token:";

    @Override
    public OrderConfirmVO confirm() {

        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();

        UserInfo userInfo = LonginInterceptors.getUserInfo();
        Long userId = userInfo.getId();
        if (userId == null) {
            return null;
        }

        //获取用户的收货地址列表,根据用户id查询收货地址
        CompletableFuture<Void> addressCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<List<MemberReceiveAddressEntity>> addressResp = this.umsClientApi.queryAddressByUserId(userId);
            List<MemberReceiveAddressEntity> address = addressResp.getData();
            orderConfirmVO.setAddresss(address);
        }, threadPoolExecutor);

        CompletableFuture<Void> cartCompletableFuture = CompletableFuture.supplyAsync(() -> {    //获取购物车中选中的商品信息
            Resp<List<Cart>> cartsResp = this.cartClientApi.queryCheckCartByUserId(userId);
            List<Cart> cartList = cartsResp.getData();
            if (CollectionUtils.isEmpty(cartList)) {
                throw new OrderException("请选择购物车中商品");
            }
            return cartList;
        }, threadPoolExecutor).thenAcceptAsync(cartList -> {
            List<OrderItemVO> itemVOS = cartList.stream().map(cart -> {
                OrderItemVO orderItemVO = new OrderItemVO();
                CompletableFuture<Void> skuCompletableFuture = CompletableFuture.runAsync(() -> {
                    Resp<SkuInfoEntity> skuInfoEntityResp = this.pmsClientApi.querySkuById(cart.getSkuId());
                    SkuInfoEntity skuInfoEntity = skuInfoEntityResp.getData();
                    if (skuInfoEntity != null) {
                        orderItemVO.setDefaultImage(skuInfoEntity.getSkuDefaultImg());
                        orderItemVO.setTitle(skuInfoEntity.getSkuTitle());
                        orderItemVO.setSkuId(cart.getSkuId());
                        orderItemVO.setWeight(skuInfoEntity.getWeight());
                        orderItemVO.setCount(cart.getCount());
                        orderItemVO.setPrice(skuInfoEntity.getPrice());
                    }
                }, threadPoolExecutor);

                CompletableFuture<Void> saleCompletableFuture = CompletableFuture.runAsync(() -> {
                    Resp<List<SkuSaleAttrValueEntity>> saleResp = this.pmsClientApi.querySkuSaleAttrValuesBySkuId(cart.getSkuId());
                    List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = saleResp.getData();
                    orderItemVO.setSaleAttrValues(skuSaleAttrValueEntities);
                }, threadPoolExecutor);

                CompletableFuture<Void> storeCompletableFuture = CompletableFuture.runAsync(() -> {
                    Resp<List<WareSkuEntity>> wareSkuBySkuIdResp = this.wmsClientApi.queryWareSkuBySkuId(cart.getSkuId());
                    List<WareSkuEntity> wareSkuEntities = wareSkuBySkuIdResp.getData();
                    if (!CollectionUtils.isEmpty(wareSkuEntities)) {
                        orderItemVO.setStore(wareSkuEntities.stream().anyMatch(wareSkuEntity -> wareSkuEntity.getStock() > 0));
                    }
                }, threadPoolExecutor);

                CompletableFuture.allOf(skuCompletableFuture, saleCompletableFuture, storeCompletableFuture).join();
                return orderItemVO;
            }).collect(Collectors.toList());
            orderConfirmVO.setOrderItemVOS(itemVOS);
        }, threadPoolExecutor);

        //查询用户信息获取积分
        CompletableFuture<Void> memberCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<MemberEntity> memberEntityResp = this.umsClientApi.queryMemberById(userId);
            MemberEntity memberEntity = memberEntityResp.getData(); //获取用户信息
            orderConfirmVO.setBounds(memberEntity.getIntegration());//用户成长积分
        }, threadPoolExecutor);

        //生成唯一标识，防止重复提交(一份相应到页面，一份保存到redis)
        CompletableFuture<Void> tokenCompletableFuture = CompletableFuture.runAsync(() -> {
            String orderToken = IdWorker.getIdStr();//使用mybatisPlus封装的雪花算法
            orderConfirmVO.setOrderToken(orderToken);
            this.redisTemplate.opsForValue().set(TOKEN_PREFIX + orderToken, orderToken);
        }, threadPoolExecutor);

        CompletableFuture.allOf(addressCompletableFuture, cartCompletableFuture, memberCompletableFuture, tokenCompletableFuture).join();

        return orderConfirmVO;
    }

    @Override
    public OrderEntity submit(OrderSubmitVO orderSubmitVO) {

        UserInfo userInfo = LonginInterceptors.getUserInfo();
        //获取orderToken
        String orderToken = orderSubmitVO.getOrderToken();
        //1.防重复提交，查询redis中有没有orderToken信息。如果有是第一次提交，放行并删除redis中orderToken.如果没有则不是第一次提交
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //通过DefaultRedisScript中的Long.class指定脚本执行返回结果类型
        Long flag = this.redisTemplate.execute(new DefaultRedisScript<>(script,Long.class), Arrays.asList(TOKEN_PREFIX + orderToken), orderToken);
        if (flag == 0) {
            throw new OrderException("订单不可重复提交");
        }

        //2.校验价格总价一致就可以
        List<OrderItemVO> itemVOS = orderSubmitVO.getItemVOS(); //送货清单
        BigDecimal totalPrice = orderSubmitVO.getTotalPrice();  //页面总价
        if (CollectionUtils.isEmpty(itemVOS)) {
            throw new OrderException("没有购买的商品，请到购物车中勾选商品");
        }
        //获取实时总价
        BigDecimal currentTotalPrice = itemVOS.stream().map(item -> {
            Resp<SkuInfoEntity> skuInfoEntityResp = this.pmsClientApi.querySkuById(item.getSkuId());
            SkuInfoEntity skuInfoEntity = skuInfoEntityResp.getData();
            if (skuInfoEntity != null) {
                return skuInfoEntity.getPrice().multiply(new BigDecimal(item.getCount()));
            }
            return new BigDecimal(0);
        }).reduce((a, b) -> a.add(b)).get();
        //判断实时总价是否和页面价格一致
        if (currentTotalPrice.compareTo(totalPrice) != 0) {
            throw new OrderException("页面已过期，请刷新页面后重新下单");
        }
        //3.校验库存是否充足,并锁定库存，一次性提示所有库存不够的商品信息
        List<SkuLockVO> lockVOS = itemVOS.stream().map(orderItemVO -> {
            SkuLockVO skuLockVO = new SkuLockVO();
            skuLockVO.setSkuId(orderItemVO.getSkuId());
            skuLockVO.setCount(orderItemVO.getCount());
            skuLockVO.setOrderTOken(orderToken);
            return skuLockVO;
        }).collect(Collectors.toList());
        Resp<Object> wareResp = this.wmsClientApi.checkAndLockStore(lockVOS);
        //如果code不等于0说明锁库存失败
        if (wareResp.getCode() != 0) {
            throw new OrderException(wareResp.getMsg());
        }

        //4.下单(创建订单及订单详情)
        Resp<OrderEntity> orderEntityResp = null;
        try {
            orderSubmitVO.setUserId(userInfo.getId());
            orderEntityResp = this.omsClientApi.saveOrder(orderSubmitVO);
            OrderEntity orderEntity = orderEntityResp.getData();
        } catch (Exception e) {
            e.printStackTrace();
            //发送消息给库存wms，解锁对应的库存
            this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","stock.unlock",orderToken);
            throw new OrderException("创建订单失败，请重新下单");
        }
        //5.删除购物车（发送消息，删除购物车）
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userInfo.getId()); //购物车用户id
        List<Long> skuIds = itemVOS.stream().map(OrderItemVO::getSkuId).collect(Collectors.toList());
        map.put("skuIds",skuIds);       //购物车下单的商品清单
        amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","cart.delete",map);

        if(orderEntityResp != null){
            return orderEntityResp.getData();
        }
        return null;
    }


}
