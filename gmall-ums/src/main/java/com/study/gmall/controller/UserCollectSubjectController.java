package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.UserCollectSubjectEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.UserCollectSubjectService;




/**
 * 关注活动表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Api(tags = "关注活动表 管理")
@RestController
@RequestMapping("ums/usercollectsubject")
public class UserCollectSubjectController {
    @Autowired
    private UserCollectSubjectService userCollectSubjectService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:usercollectsubject:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = userCollectSubjectService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:usercollectsubject:info')")
    public Resp<UserCollectSubjectEntity> info(@PathVariable("id") Long id){
		UserCollectSubjectEntity userCollectSubject = userCollectSubjectService.getById(id);

        return Resp.ok(userCollectSubject);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:usercollectsubject:save')")
    public Resp<Object> save(@RequestBody UserCollectSubjectEntity userCollectSubject){
		userCollectSubjectService.save(userCollectSubject);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:usercollectsubject:update')")
    public Resp<Object> update(@RequestBody UserCollectSubjectEntity userCollectSubject){
		userCollectSubjectService.updateById(userCollectSubject);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:usercollectsubject:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		userCollectSubjectService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
