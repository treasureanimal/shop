package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.service.CouponSpuCategoryRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.sms.entity.CouponSpuCategoryRelationEntity;




/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-12 00:36:40
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("sms/couponspucategoryrelation")
public class CouponSpuCategoryRelationController {
    @Autowired
    private CouponSpuCategoryRelationService couponSpuCategoryRelationService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:couponspucategoryrelation:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = couponSpuCategoryRelationService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:couponspucategoryrelation:info')")
    public Resp<CouponSpuCategoryRelationEntity> info(@PathVariable("id") Long id){
		CouponSpuCategoryRelationEntity couponSpuCategoryRelation = couponSpuCategoryRelationService.getById(id);

        return Resp.ok(couponSpuCategoryRelation);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:couponspucategoryrelation:save')")
    public Resp<Object> save(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation){
		couponSpuCategoryRelationService.save(couponSpuCategoryRelation);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:couponspucategoryrelation:update')")
    public Resp<Object> update(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation){
		couponSpuCategoryRelationService.updateById(couponSpuCategoryRelation);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:couponspucategoryrelation:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		couponSpuCategoryRelationService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
