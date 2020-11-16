package com.study.gmall.pms.vo;

import com.study.gmall.pms.entity.SpuInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class SpuInfoVO extends SpuInfoEntity {
    private List<String> spuImages;
    private List<BaseAttrVO> baseAttrs;
    private List<?> skus;
}
