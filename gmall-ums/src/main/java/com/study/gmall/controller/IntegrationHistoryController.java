package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.IntegrationHistoryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.IntegrationHistoryService;




/**
 * 购物积分记录表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Api(tags = "购物积分记录表 管理")
@RestController
@RequestMapping("ums/integrationhistory")
public class IntegrationHistoryController {
    @Autowired
    private IntegrationHistoryService integrationHistoryService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:integrationhistory:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = integrationHistoryService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:integrationhistory:info')")
    public Resp<IntegrationHistoryEntity> info(@PathVariable("id") Long id){
		IntegrationHistoryEntity integrationHistory = integrationHistoryService.getById(id);

        return Resp.ok(integrationHistory);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:integrationhistory:save')")
    public Resp<Object> save(@RequestBody IntegrationHistoryEntity integrationHistory){
		integrationHistoryService.save(integrationHistory);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:integrationhistory:update')")
    public Resp<Object> update(@RequestBody IntegrationHistoryEntity integrationHistory){
		integrationHistoryService.updateById(integrationHistory);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:integrationhistory:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		integrationHistoryService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
