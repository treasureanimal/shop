package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.gmall.sms.entity.SkuFullReductionEntity;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;


/**
 * 商品满减信息
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-12 00:36:40
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageVo queryPage(QueryCondition params);
}

