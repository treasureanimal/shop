package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.GrowthHistoryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.GrowthHistoryService;




/**
 * 成长积分记录表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Api(tags = "成长积分记录表 管理")
@RestController
@RequestMapping("ums/growthhistory")
public class GrowthHistoryController {
    @Autowired
    private GrowthHistoryService growthHistoryService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:growthhistory:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = growthHistoryService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:growthhistory:info')")
    public Resp<GrowthHistoryEntity> info(@PathVariable("id") Long id){
		GrowthHistoryEntity growthHistory = growthHistoryService.getById(id);

        return Resp.ok(growthHistory);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:growthhistory:save')")
    public Resp<Object> save(@RequestBody GrowthHistoryEntity growthHistory){
		growthHistoryService.save(growthHistory);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:growthhistory:update')")
    public Resp<Object> update(@RequestBody GrowthHistoryEntity growthHistory){
		growthHistoryService.updateById(growthHistory);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:growthhistory:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		growthHistoryService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
