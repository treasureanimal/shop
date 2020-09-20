package com.study.gmall.service.impl;

import com.study.gmall.sms.entity.SeckillSkuEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;

import com.study.gmall.dao.SeckillSkuDao;
import com.study.gmall.service.SeckillSkuService;


@Service("seckillSkuService")
public class SeckillSkuServiceImpl extends ServiceImpl<SeckillSkuDao, SeckillSkuEntity> implements SeckillSkuService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SeckillSkuEntity> page = this.page(
                new Query<SeckillSkuEntity>().getPage(params),
                new QueryWrapper<SeckillSkuEntity>()
        );

        return new PageVo(page);
    }

}