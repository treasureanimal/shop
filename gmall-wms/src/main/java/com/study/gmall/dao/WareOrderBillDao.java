package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.wms.entity.WareOrderBillEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存工作单
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:35:39
 */
@Mapper
public interface WareOrderBillDao extends BaseMapper<WareOrderBillEntity> {
	
}
