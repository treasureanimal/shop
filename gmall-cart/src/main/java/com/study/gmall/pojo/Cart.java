package com.study.gmall.pojo;

import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.study.gmall.sms.vo.SaleVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Cart {

    private Integer skuId;
    private String title;
    private String defaultImage;
    private BigDecimal price;
    private Integer count;
    private Boolean store;
    private List<SkuSaleAttrValueEntity> saleAttrValues;
    private List<SaleVO> sales;
    private Boolean check;

}
