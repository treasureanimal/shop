package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.service.CategoryBoundsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.sms.entity.CategoryBoundsEntity;




/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-12 00:36:39
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("sms/categorybounds")
public class CategoryBoundsController {
    @Autowired
    private CategoryBoundsService categoryBoundsService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:categorybounds:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = categoryBoundsService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:categorybounds:info')")
    public Resp<CategoryBoundsEntity> info(@PathVariable("id") Long id){
		CategoryBoundsEntity categoryBounds = categoryBoundsService.getById(id);

        return Resp.ok(categoryBounds);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:categorybounds:save')")
    public Resp<Object> save(@RequestBody CategoryBoundsEntity categoryBounds){
		categoryBoundsService.save(categoryBounds);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:categorybounds:update')")
    public Resp<Object> update(@RequestBody CategoryBoundsEntity categoryBounds){
		categoryBoundsService.updateById(categoryBounds);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:categorybounds:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		categoryBoundsService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
