package com.study.gmall.service.impl;

import com.study.gmall.dao.CategoryBoundsDao;
import com.study.gmall.service.CategoryBoundsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.sms.entity.CategoryBoundsEntity;


@Service("categoryBoundsService")
public class CategoryBoundsServiceImpl extends ServiceImpl<CategoryBoundsDao, CategoryBoundsEntity> implements CategoryBoundsService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CategoryBoundsEntity> page = this.page(
                new Query<CategoryBoundsEntity>().getPage(params),
                new QueryWrapper<CategoryBoundsEntity>()
        );

        return new PageVo(page);
    }

}