package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.dao.ProductAttrValueDao;
import com.study.gmall.dao.SpuInfoDao;
import com.study.gmall.dao.SpuInfoDescDao;
import com.study.gmall.pms.entity.ProductAttrValueEntity;
import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.study.gmall.pms.entity.SpuInfoDescEntity;
import com.study.gmall.pms.entity.SpuInfoEntity;
import com.study.gmall.pms.vo.BaseAttrVO;
import com.study.gmall.pms.vo.SpuInfoVO;
import com.study.gmall.service.ProductAttrValueService;
import com.study.gmall.service.SpuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescDao descDao;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo querySpuPage(QueryCondition condition, Long cid) {
        QueryWrapper<SpuInfoEntity> spuInfoEntityQueryWrapper = new QueryWrapper<>();
        if (cid != 0) {
            spuInfoEntityQueryWrapper.eq("catalog_id",cid);
        }
        String key = condition.getKey();
        if (StringUtils.isNotBlank(key)) {
            spuInfoEntityQueryWrapper.and(t -> t.eq("id",key).or().like("spu_name",key));
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(condition),
                spuInfoEntityQueryWrapper
        );

        return new PageVo(page);
    }

    @Override
    public void bigSave(SpuInfoVO spuInfoVO) {
        /*1.保存spu相关的3张表*/
        //1.1.保存pms_spu_info信息
        this.save(spuInfoVO);
        Long spuId = spuInfoVO.getId();
        //1.2.保存pms_spu_info_desc信息
        List<String> spuImages = spuInfoVO.getSpuImages();
        if (!CollectionUtils.isEmpty(spuImages)) {
            SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
            spuInfoDescEntity.setSpuId(spuId);
            spuInfoDescEntity.setDecript(StringUtils.join(spuImages,","));
            descDao.insert(spuInfoDescEntity);
        }
        //1.3.保存pms_product_attr_value信息
        List<BaseAttrVO> baseAttrs = spuInfoVO.getBaseAttrs();
        if (!CollectionUtils.isEmpty(baseAttrs)) {
            List<ProductAttrValueEntity> collect = baseAttrs.stream().map(baseAttrVO -> {
                baseAttrVO.setSpuId(spuId);
                return (ProductAttrValueEntity) baseAttrVO;
            }).collect(Collectors.toList());
            productAttrValueService.saveBatch(collect);
        }
        //2.保存sku相关的3张表
        //2.1.保存pms_sku_info
        //2.2.保存pms_sku_images
        //2.3.保存pms_sale_attr_value
        //3.保存营销信息的3张表
        //3.1.保存sms_sku_bounds
        //3.2.保存sms_sku_ladder
        //3.3.保存sms_sku_full_reduction
    }

}