package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.gmall.pms.entity.ProductAttrValueEntity;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;

import java.util.List;


/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 20:43:49
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageVo queryPage(QueryCondition params);

    List<ProductAttrValueEntity> querySearchAttrValueBySpuId(Long spuId);
}

