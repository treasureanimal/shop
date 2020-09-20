package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.oms.entity.OrderEntity;


/**
 * 订单
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:30:20
 */
public interface OrderService extends IService<OrderEntity> {

    PageVo queryPage(QueryCondition params);
}

