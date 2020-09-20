package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.pms.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
