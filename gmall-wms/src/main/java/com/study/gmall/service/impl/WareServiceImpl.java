package com.study.gmall.service.impl;

import com.study.gmall.dao.WareDao;
import com.study.gmall.service.WareService;
import com.study.gmall.wms.entity.WareEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;


@Service("wareService")
public class WareServiceImpl extends ServiceImpl<WareDao, WareEntity> implements WareService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<WareEntity> page = this.page(
                new Query<WareEntity>().getPage(params),
                new QueryWrapper<WareEntity>()
        );

        return new PageVo(page);
    }

}