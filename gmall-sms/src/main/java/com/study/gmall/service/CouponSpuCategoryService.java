package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.sms.entity.CouponSpuCategoryEntity;


/**
 * 优惠券分类关联
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:32
 */
public interface CouponSpuCategoryService extends IService<CouponSpuCategoryEntity> {

    PageVo queryPage(QueryCondition params);
}

