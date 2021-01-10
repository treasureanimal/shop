package com.study.gmall.service;

import com.study.gmall.pms.entity.CategoryEntity;
import com.study.gmall.pms.vo.CategoryVO;

import java.util.List;

public interface IndexService {
    List<CategoryEntity> queryLV1CateGories();

    List<CategoryVO> querySubCategories(Long pid);
}
