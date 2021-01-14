package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.dao.AttrAttrgroupRelationDao;
import com.study.gmall.dao.AttrDao;
import com.study.gmall.dao.AttrGroupDao;
import com.study.gmall.dao.ProductAttrValueDao;
import com.study.gmall.item.ItemGroupVO;
import com.study.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.study.gmall.pms.entity.AttrEntity;
import com.study.gmall.pms.entity.AttrGroupEntity;
import com.study.gmall.pms.entity.ProductAttrValueEntity;
import com.study.gmall.pms.vo.AttrGroupVO;
import com.study.gmall.service.AttrGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private AttrAttrgroupRelationDao relationDao;
    @Autowired
    private AttrDao attrDao;
    @Autowired
    private ProductAttrValueDao attrValueDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryByCidPage(Long cid, QueryCondition condition) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(condition),
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cid)
        );

        return new PageVo(page);
    }

    @Override
    public AttrGroupVO queryById(Long gid) {
        // 查询分组
        AttrGroupVO attrGroupVO = new AttrGroupVO();
        AttrGroupEntity attrGroupEntity = this.attrGroupDao.selectById(gid);
        BeanUtils.copyProperties(attrGroupEntity, attrGroupVO);

        // 查询分组下的关联关系
        List<AttrAttrgroupRelationEntity> relations = this.relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
        // 判断关联关系是否为空，如果为空，直接返回
        if (CollectionUtils.isEmpty(relations)){
            return attrGroupVO;
        }
        attrGroupVO.setRelations(relations);

        // 收集分组下的所有规格id
        List<Long> attrIds = relations.stream().map(relation -> relation.getAttrId()).collect(Collectors.toList());
        // 查询分组下的所有规格参数
        List<AttrEntity> attrEntities = this.attrDao.selectBatchIds(attrIds);
        attrGroupVO.setAttrEntities(attrEntities);

        return attrGroupVO;
    }

    @Override
    public List<AttrGroupVO> queryGroupByCid(Long catId) {
        //根据cid查询三级分类下的所有属性分组
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        //1.根据分组id查询中间表
        //2.根据中间表中的attrIds查询参数
        //3.数据类型的转换 AttrGroupEntity --> AttrGroupVO
        return attrGroupEntities.stream().map(attrGroupEntity -> this.queryById(attrGroupEntity.getAttrGroupId())).collect(Collectors.toList());
    }

    @Override
    public List<ItemGroupVO> queryItemGroupVOByCidAndSpuId(Long cid, Long spuId) {
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cid));
        return attrGroupEntities.stream().map(group ->{
            ItemGroupVO itemGroupVO = new ItemGroupVO();
            itemGroupVO.setName (group.getAttrGroupName());

            //查询规格参数的值
            List<AttrAttrgroupRelationEntity> relationEntities = this.relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", group.getAttrGroupId()));
            List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

            List<ProductAttrValueEntity> productAttrValueEntities = this.attrValueDao.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId).in("attr_id", attrIds));
            itemGroupVO.setBaseAttrs(productAttrValueEntities);

            return itemGroupVO;
        }).collect(Collectors.toList());
    }

}