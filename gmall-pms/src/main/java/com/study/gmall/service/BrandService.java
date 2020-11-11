package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.pms.entity.BrandEntity;


/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 13:55:21
 */
public interface BrandService extends IService<BrandEntity> {

    PageVo queryPage(QueryCondition params);
}

