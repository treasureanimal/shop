package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.sms.entity.CouponHistoryEntity;


/**
 * 优惠券领取历史记录
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:31
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageVo queryPage(QueryCondition params);
}

