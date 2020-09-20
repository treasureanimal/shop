package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.SkuEntity;
import com.study.gmall.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * sku信息
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Api(tags = "sku信息 管理")
@RestController
@RequestMapping("pms/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:sku:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = skuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:sku:info')")
    public Resp<SkuEntity> info(@PathVariable("id") Long id){
		SkuEntity sku = skuService.getById(id);

        return Resp.ok(sku);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:sku:save')")
    public Resp<Object> save(@RequestBody SkuEntity sku){
		skuService.save(sku);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:sku:update')")
    public Resp<Object> update(@RequestBody SkuEntity sku){
		skuService.updateById(sku);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:sku:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		skuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
