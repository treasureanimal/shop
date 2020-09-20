package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.UserLevelEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.UserLevelService;




/**
 * 会员等级表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Api(tags = "会员等级表 管理")
@RestController
@RequestMapping("ums/userlevel")
public class UserLevelController {
    @Autowired
    private UserLevelService userLevelService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:userlevel:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = userLevelService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:userlevel:info')")
    public Resp<UserLevelEntity> info(@PathVariable("id") Long id){
		UserLevelEntity userLevel = userLevelService.getById(id);

        return Resp.ok(userLevel);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:userlevel:save')")
    public Resp<Object> save(@RequestBody UserLevelEntity userLevel){
		userLevelService.save(userLevel);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:userlevel:update')")
    public Resp<Object> update(@RequestBody UserLevelEntity userLevel){
		userLevelService.updateById(userLevel);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:userlevel:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		userLevelService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
