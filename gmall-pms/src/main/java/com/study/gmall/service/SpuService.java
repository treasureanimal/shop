package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.pms.entity.SpuEntity;


/**
 * spu信息
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
public interface SpuService extends IService<SpuEntity> {

    PageVo queryPage(QueryCondition params);
}

