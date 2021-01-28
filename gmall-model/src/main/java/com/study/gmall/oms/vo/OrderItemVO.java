package com.study.gmall.oms.vo;

import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.study.gmall.sms.vo.SaleVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemVO {

    private Long skuId;
    private String title;
    private String defaultImage;
    private BigDecimal price; //数据库中取最新价格
    private Integer count;
    private Boolean store;
    private List<SkuSaleAttrValueEntity> saleAttrValues;
    private List<SaleVO> sales;
    private BigDecimal weight;
}