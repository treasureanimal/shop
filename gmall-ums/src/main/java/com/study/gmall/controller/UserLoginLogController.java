package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.UserLoginLogEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.UserLoginLogService;




/**
 * 用户登陆记录表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Api(tags = "用户登陆记录表 管理")
@RestController
@RequestMapping("ums/userloginlog")
public class UserLoginLogController {
    @Autowired
    private UserLoginLogService userLoginLogService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:userloginlog:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = userLoginLogService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:userloginlog:info')")
    public Resp<UserLoginLogEntity> info(@PathVariable("id") Long id){
		UserLoginLogEntity userLoginLog = userLoginLogService.getById(id);

        return Resp.ok(userLoginLog);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:userloginlog:save')")
    public Resp<Object> save(@RequestBody UserLoginLogEntity userLoginLog){
		userLoginLogService.save(userLoginLog);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:userloginlog:update')")
    public Resp<Object> update(@RequestBody UserLoginLogEntity userLoginLog){
		userLoginLogService.updateById(userLoginLog);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:userloginlog:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		userLoginLogService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
