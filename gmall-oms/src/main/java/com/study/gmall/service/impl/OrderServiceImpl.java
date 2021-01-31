package com.study.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.dao.OrderDao;
import com.study.gmall.dao.OrderItemDao;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.feign.GmallUmsClientApi;
import com.study.gmall.oms.entity.OrderEntity;
import com.study.gmall.oms.entity.OrderItemEntity;
import com.study.gmall.oms.vo.OrderItemVO;
import com.study.gmall.oms.vo.OrderSubmitVO;
import com.study.gmall.pms.entity.SkuInfoEntity;
import com.study.gmall.pms.entity.SpuInfoEntity;
import com.study.gmall.service.OrderService;
import com.study.gmall.ums.entity.MemberEntity;
import com.study.gmall.ums.entity.MemberReceiveAddressEntity;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private GmallUmsClientApi umsClientApi;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private GmallPmsClientApi pmsApi;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public OrderEntity saveOrder(OrderSubmitVO orderSubmitVO) {

        //保存orderEntity
        MemberReceiveAddressEntity address = orderSubmitVO.getAddress();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setReceiverRegion(address.getRegion());
        orderEntity.setReceiverProvince(address.getProvince());
        orderEntity.setReceiverCity(address.getCity());
        orderEntity.setReceiverName(address.getName());
        orderEntity.setReceiverPhone(address.getPhone());
        orderEntity.setReceiverPostCode(address.getPostCode());
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());

        Resp<MemberEntity> memberEntityResp = this.umsClientApi.queryMemberById(orderSubmitVO.getUserId());
        MemberEntity memberEntity = memberEntityResp.getData();
        orderEntity.setMemberUsername(memberEntity.getUsername());
        orderEntity.setMemberId(orderSubmitVO.getUserId());
        //清算每个商品赠送积分之和
        orderEntity.setIntegration(0);
        orderEntity.setGrowth(0);
        orderEntity.setDeleteStatus(0);
        orderEntity.setStatus(0);
        orderEntity.setCreateTime(new Date());
        orderEntity.setModifyTime(orderEntity.getCreateTime());
        orderEntity.setDeliveryCompany(orderSubmitVO.getDeliveryCompany());
        orderEntity.setSourceType(1);
        orderEntity.setPayType(orderSubmitVO.getPayType());
        orderEntity.setTotalAmount(orderSubmitVO.getTotalPrice());
        orderEntity.setOrderSn(orderSubmitVO.getOrderToken());
        this.save(orderEntity);
        Long orderId = orderEntity.getId();

        // 保存订单详情OrderItemEntity
        List<OrderItemVO> items = orderSubmitVO.getItemVOS();
        items.forEach(item -> {
            OrderItemEntity itemEntity = new OrderItemEntity();
            itemEntity.setSkuId(item.getSkuId());
            Resp<SkuInfoEntity> skuInfoEntityResp = this.pmsApi.querySkuById(item.getSkuId());
            SkuInfoEntity skuInfoEntity = skuInfoEntityResp.getData();

            Resp<SpuInfoEntity> spuInfoEntityResp = this.pmsApi.querySpuById(skuInfoEntity.getSpuId());
            SpuInfoEntity spuInfoEntity = spuInfoEntityResp.getData();

            itemEntity.setSkuPrice(skuInfoEntity.getPrice());
            itemEntity.setSkuAttrsVals(JSON.toJSONString(item.getSaleAttrValues()));
            itemEntity.setCategoryId(skuInfoEntity.getCatalogId());
            itemEntity.setOrderId(orderId);
            itemEntity.setOrderSn(orderSubmitVO.getOrderToken());
            itemEntity.setSpuId(spuInfoEntity.getId());
            itemEntity.setSkuName(skuInfoEntity.getSkuName());
            itemEntity.setSkuPic(skuInfoEntity.getSkuDefaultImg());
            itemEntity.setSkuQuantity(item.getCount());
            itemEntity.setSpuName(spuInfoEntity.getSpuName());

            this.orderItemDao.insert(itemEntity);
        });
        //订单创建之后响应之前发送延时消息，达到定时关单
this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","order.ttl",orderSubmitVO.getOrderToken());
        return null;
    }
}