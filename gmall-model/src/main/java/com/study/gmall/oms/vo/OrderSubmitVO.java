package com.study.gmall.oms.vo;

import com.study.gmall.ums.entity.MemberReceiveAddressEntity;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderSubmitVO {

    private String orderToken;      //防止重复提交
    private MemberReceiveAddressEntity address; //配货地址
    private Integer payType;        //支付方式
    private String deliveryCompany; //物流公司
    private List<OrderItemVO> itemVOS;//商品清单
    private Integer bounds;        //积分
    private BigDecimal totalPrice;  //校验价格
    private Long userId;
}
