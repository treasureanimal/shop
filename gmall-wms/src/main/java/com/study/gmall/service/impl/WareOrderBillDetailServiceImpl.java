package com.study.gmall.service.impl;

import com.study.gmall.dao.WareOrderBillDetailDao;
import com.study.gmall.service.WareOrderBillDetailService;
import com.study.gmall.wms.entity.WareOrderBillDetailEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;


@Service("wareOrderBillDetailService")
public class WareOrderBillDetailServiceImpl extends ServiceImpl<WareOrderBillDetailDao, WareOrderBillDetailEntity> implements WareOrderBillDetailService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<WareOrderBillDetailEntity> page = this.page(
                new Query<WareOrderBillDetailEntity>().getPage(params),
                new QueryWrapper<WareOrderBillDetailEntity>()
        );

        return new PageVo(page);
    }

}