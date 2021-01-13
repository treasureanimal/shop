package com.study.gmall.item;

import com.study.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;

import java.util.List;

@Data
public class ItemGroupVO {

    private String name;
    private List<ProductAttrValueEntity> baseAttrs;
}
