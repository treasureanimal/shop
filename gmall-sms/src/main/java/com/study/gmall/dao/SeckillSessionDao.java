package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.sms.entity.SeckillSessionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动场次
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:31
 */
@Mapper
public interface SeckillSessionDao extends BaseMapper<SeckillSessionEntity> {
	
}
