package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.gmall.item.ItemGroupVO;
import com.study.gmall.pms.entity.AttrGroupEntity;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.pms.vo.AttrGroupVO;

import java.util.List;


/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 20:43:49
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryByCidPage(Long cid, QueryCondition condition);

    AttrGroupVO queryById(Long gid);

    List<AttrGroupVO> queryGroupByCid(Long catId);

    List<ItemGroupVO> queryItemGroupVOByCidAndSpuId(Long cid, Long spuId);
}

