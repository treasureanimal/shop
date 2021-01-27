package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.study.core.bean.Resp;
import com.study.core.exception.OrderException;
import com.study.gmall.cart.pojo.Cart;
import com.study.gmall.cart.pojo.UserInfo;
import com.study.gmall.feign.*;
import com.study.gmall.interceptors.LonginInterceptors;
import com.study.gmall.oms.vo.OrderConfirmVO;
import com.study.gmall.oms.vo.OrderItemVO;
import com.study.gmall.pms.entity.SkuInfoEntity;
import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.study.gmall.service.OrderService;
import com.study.gmall.ums.entity.MemberEntity;
import com.study.gmall.ums.entity.MemberReceiveAddressEntity;
import com.study.gmall.wms.entity.WareSkuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
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
        }, threadPoolExecutor);

        CompletableFuture.allOf(addressCompletableFuture,cartCompletableFuture,memberCompletableFuture,tokenCompletableFuture).join();
        return orderConfirmVO;
    }
}
