package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.SpuDescEntity;
import com.study.gmall.service.SpuDescService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * spu信息介绍
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Api(tags = "spu信息介绍 管理")
@RestController
@RequestMapping("pms/spudesc")
public class SpuDescController {
    @Autowired
    private SpuDescService spuDescService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:spudesc:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = spuDescService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{spuId}")
    @PreAuthorize("hasAuthority('pms:spudesc:info')")
    public Resp<SpuDescEntity> info(@PathVariable("spuId") Long spuId){
		SpuDescEntity spuDesc = spuDescService.getById(spuId);

        return Resp.ok(spuDesc);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spudesc:save')")
    public Resp<Object> save(@RequestBody SpuDescEntity spuDesc){
		spuDescService.save(spuDesc);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:spudesc:update')")
    public Resp<Object> update(@RequestBody SpuDescEntity spuDesc){
		spuDescService.updateById(spuDesc);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:spudesc:delete')")
    public Resp<Object> delete(@RequestBody Long[] spuIds){
		spuDescService.removeByIds(Arrays.asList(spuIds));

        return Resp.ok(null);
    }

}
