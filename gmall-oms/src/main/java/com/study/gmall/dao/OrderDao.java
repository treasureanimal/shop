package com.study.gmall.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.oms.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:30:20
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
