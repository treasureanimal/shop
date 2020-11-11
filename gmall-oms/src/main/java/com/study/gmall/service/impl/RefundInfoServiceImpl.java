package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.dao.RefundInfoDao;
import com.study.gmall.oms.entity.RefundInfoEntity;
import com.study.gmall.service.RefundInfoService;
import org.springframework.stereotype.Service;


@Service("refundInfoService")
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoDao, RefundInfoEntity> implements RefundInfoService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<RefundInfoEntity> page = this.page(
                new Query<RefundInfoEntity>().getPage(params),
                new QueryWrapper<RefundInfoEntity>()
        );

        return new PageVo(page);
    }

}