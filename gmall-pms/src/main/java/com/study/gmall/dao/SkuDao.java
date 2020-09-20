package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.pms.entity.SkuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sku信息
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Mapper
public interface SkuDao extends BaseMapper<SkuEntity> {
	
}
