package com.study.gmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gmall.wms.entity.WareEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:35:39
 */
@Mapper
public interface WareDao extends BaseMapper<WareEntity> {
	
}
