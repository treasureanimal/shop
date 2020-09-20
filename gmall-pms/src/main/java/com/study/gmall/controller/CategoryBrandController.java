package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.CategoryBrandEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.CategoryBrandService;




/**
 * 品牌分类关联
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Api(tags = "品牌分类关联 管理")
@RestController
@RequestMapping("pms/categorybrand")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:categorybrand:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = categoryBrandService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:categorybrand:info')")
    public Resp<CategoryBrandEntity> info(@PathVariable("id") Long id){
		CategoryBrandEntity categoryBrand = categoryBrandService.getById(id);

        return Resp.ok(categoryBrand);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:categorybrand:save')")
    public Resp<Object> save(@RequestBody CategoryBrandEntity categoryBrand){
		categoryBrandService.save(categoryBrand);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:categorybrand:update')")
    public Resp<Object> update(@RequestBody CategoryBrandEntity categoryBrand){
		categoryBrandService.updateById(categoryBrand);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:categorybrand:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		categoryBrandService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
