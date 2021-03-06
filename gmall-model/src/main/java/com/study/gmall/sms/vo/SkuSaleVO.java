package com.study.gmall.sms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 营销属性
 */
@Data
public class SkuSaleVO {

    //积分营销相关字段
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
    private List<Integer> work;
    //打折相关字段
    private Integer fullCount;
    private BigDecimal discount;
    private Integer ladderAddOther;
    //满减相关字段
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer fullAddOther;

    private Long skuId;
}
