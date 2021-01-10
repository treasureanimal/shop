package com.study.gmall.pms.vo;

import com.study.gmall.pms.entity.CategoryEntity;
import lombok.Data;
import java.util.List;

@Data
public class CategoryVO extends CategoryEntity {

    private List<CategoryEntity> subs;
}
