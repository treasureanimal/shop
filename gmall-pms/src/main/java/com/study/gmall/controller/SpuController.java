package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.SpuEntity;
import com.study.gmall.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * spu信息
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Api(tags = "spu信息 管理")
@RestController
@RequestMapping("pms/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:spu:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = spuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:spu:info')")
    public Resp<SpuEntity> info(@PathVariable("id") Long id){
		SpuEntity spu = spuService.getById(id);

        return Resp.ok(spu);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spu:save')")
    public Resp<Object> save(@RequestBody SpuEntity spu){
		spuService.save(spu);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:spu:update')")
    public Resp<Object> update(@RequestBody SpuEntity spu){
		spuService.updateById(spu);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:spu:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		spuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
