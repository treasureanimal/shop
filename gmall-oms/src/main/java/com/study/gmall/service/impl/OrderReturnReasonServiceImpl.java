package com.study.gmall.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;

import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.dao.OrderReturnReasonDao;

import com.study.gmall.oms.entity.OrderReturnReasonEntity;
import com.study.gmall.service.OrderReturnReasonService;
import org.springframework.stereotype.Service;


@Service("orderReturnReasonService")
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonDao, OrderReturnReasonEntity> implements OrderReturnReasonService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<OrderReturnReasonEntity> page = this.page(
                new Query<OrderReturnReasonEntity>().getPage(params),
                new QueryWrapper<OrderReturnReasonEntity>()
        );

        return new PageVo(page);
    }

}