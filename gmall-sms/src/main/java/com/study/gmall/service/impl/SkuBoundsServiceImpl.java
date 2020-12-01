package com.study.gmall.service.impl;

import com.study.gmall.dao.SkuBoundsDao;
import com.study.gmall.dao.SkuFullReductionDao;
import com.study.gmall.dao.SkuLadderDao;
import com.study.gmall.service.SkuBoundsService;
import com.study.gmall.sms.entity.SkuFullReductionEntity;
import com.study.gmall.sms.entity.SkuLadderEntity;
import com.study.gmall.sms.vo.SkuSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.sms.entity.SkuBoundsEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("skuBoundsService")
public class SkuBoundsServiceImpl extends ServiceImpl<SkuBoundsDao, SkuBoundsEntity> implements SkuBoundsService {

    @Autowired
    private SkuLadderDao skuLadderDao;
    @Autowired
    private SkuFullReductionDao skuFullReductionDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuBoundsEntity> page = this.page(
                new Query<SkuBoundsEntity>().getPage(params),
                new QueryWrapper<SkuBoundsEntity>()
        );

        return new PageVo(page);
    }

    @Override
    @Transactional
    public void saveSale(SkuSaleVO skuSaleVO) {
        //3.1.保存sms_sku_bounds
        SkuBoundsEntity skuBoundsEntity = new SkuBoundsEntity();
        skuBoundsEntity.setBuyBounds(skuSaleVO.getBuyBounds());
        skuBoundsEntity.setGrowBounds(skuSaleVO.getGrowBounds());
        List<Integer> work = skuSaleVO.getWork();
        skuBoundsEntity.setWork(work.get(3) +work.get(2)*2+work.get(1)*4+work.get(0)*8);
        skuBoundsEntity.setSkuId(skuSaleVO.getSkuId());
        this.save(skuBoundsEntity);
        //3.2.保存sms_sku_ladder
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuSaleVO.getSkuId());
        skuLadderEntity.setAddOther(skuSaleVO.getLadderAddOther());
        skuLadderEntity.setDiscount(skuSaleVO.getDiscount());
        skuLadderEntity.setFullCount(skuSaleVO.getFullCount());
        skuLadderDao.insert(skuLadderEntity);
        //3.3.保存sms_sku_full_reduction
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        skuFullReductionEntity.setSkuId(skuSaleVO.getSkuId());
        skuFullReductionEntity.setAddOther(skuSaleVO.getFullAddOther());
        skuFullReductionEntity.setReducePrice(skuSaleVO.getReducePrice());
        skuFullReductionEntity.setFullPrice(skuSaleVO.getFullPrice());
        skuFullReductionDao.insert(skuFullReductionEntity);
    }

}