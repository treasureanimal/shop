package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.BrandEntity;
import com.study.gmall.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 品牌
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Api(tags = "品牌 管理")
@RestController
@RequestMapping("pms/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:brand:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = brandService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:brand:info')")
    public Resp<BrandEntity> info(@PathVariable("id") Long id){
		BrandEntity brand = brandService.getById(id);

        return Resp.ok(brand);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:brand:save')")
    public Resp<Object> save(@RequestBody BrandEntity brand){
		brandService.save(brand);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public Resp<Object> update(@RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		brandService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
