package com.study.gmall.dao;

import com.study.gmall.wms.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-12 09:42:58
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    List<WareSkuEntity> checkStore(@Param("skuId") Long skuId, @Param("count") Integer count);

    int lockStore(@Param("id") Long id, @Param("count") Integer count);

    int unlockStore(@Param("wareSkuId") Long skuId,@Param("count") Integer count);

    int minusStore(@Param("wareSkuId") Long skuId,@Param("count") Integer count);
}
