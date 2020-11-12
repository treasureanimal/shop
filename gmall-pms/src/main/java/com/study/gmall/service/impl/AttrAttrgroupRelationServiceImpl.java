package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.dao.AttrAttrgroupRelationDao;
import com.study.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.study.gmall.service.AttrAttrgroupRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageVo(page);
    }

    @Override
    @Transactional
    public void delete(List<AttrAttrgroupRelationEntity> relationEntities) {
        relationEntities.forEach(relationEntity -> {
            this.remove(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", relationEntity.getAttrId())
                    .eq("attr_group_id", relationEntity.getAttrGroupId())
            );
        });
    }

}