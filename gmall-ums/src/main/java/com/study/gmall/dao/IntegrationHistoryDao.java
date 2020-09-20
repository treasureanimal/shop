package com.study.gmall.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.ums.entity.IntegrationHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物积分记录表
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Mapper
public interface IntegrationHistoryDao extends BaseMapper<IntegrationHistoryEntity> {
	
}
