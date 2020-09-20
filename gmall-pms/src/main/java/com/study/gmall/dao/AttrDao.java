package com.study.gmall.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.pms.entity.AttrEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
	
}
