package com.study.gmall.pms.vo;

import com.study.gmall.pms.entity.SkuInfoEntity;
import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuInfoVO extends SkuInfoEntity {

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
    //销售属性及值
    private List<SkuSaleAttrValueEntity> saleAttrs;
}
