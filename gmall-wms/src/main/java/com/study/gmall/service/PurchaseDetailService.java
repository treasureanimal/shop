package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.wms.entity.PurchaseDetailEntity;


/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:35:39
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageVo queryPage(QueryCondition params);
}

