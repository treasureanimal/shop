package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.sms.entity.CouponSpuCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券分类关联
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:32
 */
@Mapper
public interface CouponSpuCategoryDao extends BaseMapper<CouponSpuCategoryEntity> {
	
}
