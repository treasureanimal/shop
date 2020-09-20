package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.ums.entity.UserCollectSkuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 关注商品表
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Mapper
public interface UserCollectSkuDao extends BaseMapper<UserCollectSkuEntity> {
	
}
