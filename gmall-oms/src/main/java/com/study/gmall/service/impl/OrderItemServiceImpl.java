package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.dao.OrderItemDao;
import com.study.gmall.oms.entity.OrderItemEntity;
import com.study.gmall.service.OrderItemService;
import org.springframework.stereotype.Service;


@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageVo(page);
    }

}