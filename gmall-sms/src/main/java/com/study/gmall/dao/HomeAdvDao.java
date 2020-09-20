package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.sms.entity.HomeAdvEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页轮播广告
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:32
 */
@Mapper
public interface HomeAdvDao extends BaseMapper<HomeAdvEntity> {
	
}
