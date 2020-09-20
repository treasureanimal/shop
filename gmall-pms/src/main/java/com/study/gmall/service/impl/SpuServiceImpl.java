package com.study.gmall.service.impl;

import com.study.gmall.pms.entity.SpuEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;

import com.study.gmall.dao.SpuDao;
import com.study.gmall.service.SpuService;


@Service("spuService")
public class SpuServiceImpl extends ServiceImpl<SpuDao, SpuEntity> implements SpuService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuEntity> page = this.page(
                new Query<SpuEntity>().getPage(params),
                new QueryWrapper<SpuEntity>()
        );

        return new PageVo(page);
    }

}