package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.ums.entity.GrowthHistoryEntity;


/**
 * 成长积分记录表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
public interface GrowthHistoryService extends IService<GrowthHistoryEntity> {

    PageVo queryPage(QueryCondition params);
}

