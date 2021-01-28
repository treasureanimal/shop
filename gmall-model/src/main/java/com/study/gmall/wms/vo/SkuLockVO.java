package com.study.gmall.wms.vo;

import lombok.Data;

@Data
public class SkuLockVO {

    private Long skuId; //需要锁定的商品
    private Integer count;  //锁多少件
    private Boolean lock; //商品锁定状态
    private Long wareSkuId;//锁定库存的id
}
