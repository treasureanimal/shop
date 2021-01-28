package com.study.gmall.wms.vo;

import lombok.Data;

@Data
public class SkuLockVO {

    private Long skuId; //需要锁定的商品
    private Integer count;  //锁多少件
}
