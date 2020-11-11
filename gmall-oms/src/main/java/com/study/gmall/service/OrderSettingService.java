package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.gmall.oms.entity.OrderSettingEntity;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;


/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 20:33:58
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageVo queryPage(QueryCondition params);
}

