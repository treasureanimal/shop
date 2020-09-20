package com.study.gmall.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.oms.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:30:20
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
