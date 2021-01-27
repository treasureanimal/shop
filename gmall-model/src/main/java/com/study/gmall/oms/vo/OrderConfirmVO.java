package com.study.gmall.oms.vo;

import com.study.gmall.ums.entity.MemberReceiveAddressEntity;
import lombok.Data;

import java.util.List;

@Data
public class OrderConfirmVO {

    private List<MemberReceiveAddressEntity> addresss;

    private List<OrderItemVO> orderItemVOS; //购物车中商品信息

    private Integer bounds;

    private String orderToken; //唯一标识,防止重复提交


}
