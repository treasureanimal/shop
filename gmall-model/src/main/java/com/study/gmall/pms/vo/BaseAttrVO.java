package com.study.gmall.pms.vo;

import com.study.gmall.pms.entity.ProductAttrValueEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class BaseAttrVO extends ProductAttrValueEntity {

    private void setValueSelected(List<String> selected){

        if(CollectionUtils.isEmpty(selected)){
            return;
        }
        this.setAttrValue(StringUtils.join(selected,","));
    }

}
