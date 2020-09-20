package com.study.gmall.service.impl;

import com.study.gmall.sms.entity.CouponSpuCategoryEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;

import com.study.gmall.dao.CouponSpuCategoryDao;
import com.study.gmall.service.CouponSpuCategoryService;


@Service("couponSpuCategoryService")
public class CouponSpuCategoryServiceImpl extends ServiceImpl<CouponSpuCategoryDao, CouponSpuCategoryEntity> implements CouponSpuCategoryService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CouponSpuCategoryEntity> page = this.page(
                new Query<CouponSpuCategoryEntity>().getPage(params),
                new QueryWrapper<CouponSpuCategoryEntity>()
        );

        return new PageVo(page);
    }

}