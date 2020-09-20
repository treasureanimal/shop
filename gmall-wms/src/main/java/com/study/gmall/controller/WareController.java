package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.wms.entity.WareEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.WareService;




/**
 * 仓库信息
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:35:39
 */
@Api(tags = "仓库信息 管理")
@RestController
@RequestMapping("wms/ware")
public class WareController {
    @Autowired
    private WareService wareService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('wms:ware:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = wareService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('wms:ware:info')")
    public Resp<WareEntity> info(@PathVariable("id") Long id){
		WareEntity ware = wareService.getById(id);

        return Resp.ok(ware);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('wms:ware:save')")
    public Resp<Object> save(@RequestBody WareEntity ware){
		wareService.save(ware);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('wms:ware:update')")
    public Resp<Object> update(@RequestBody WareEntity ware){
		wareService.updateById(ware);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('wms:ware:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		wareService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
