package com.study.gmall.controller;

import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.SpuInfoDescEntity;
import com.study.gmall.service.SpuInfoDescService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 13:55:21
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("/spuinfodesc")
public class SpuInfoDescController {
    @Autowired
    private SpuInfoDescService spuInfoDescService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority(':spuinfodesc:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = spuInfoDescService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{spuId}")
    @PreAuthorize("hasAuthority(':spuinfodesc:info')")
    public Resp<SpuInfoDescEntity> info(@PathVariable("spuId") Long spuId) {
        SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(spuId);

        return Resp.ok(spuInfoDesc);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority(':spuinfodesc:save')")
    public Resp<Object> save(@RequestBody SpuInfoDescEntity spuInfoDesc) {
        spuInfoDescService.save(spuInfoDesc);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority(':spuinfodesc:update')")
    public Resp<Object> update(@RequestBody SpuInfoDescEntity spuInfoDesc) {
        spuInfoDescService.updateById(spuInfoDesc);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(':spuinfodesc:delete')")
    public Resp<Object> delete(@RequestBody Long[] spuIds) {
        spuInfoDescService.removeByIds(Arrays.asList(spuIds));

        return Resp.ok(null);
    }

}
