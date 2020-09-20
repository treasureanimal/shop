package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.oms.entity.OrderSettingEntity;


/**
 * 订单配置信息
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:30:20
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageVo queryPage(QueryCondition params);
}

