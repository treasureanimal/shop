package com.study.gmall.controller;

import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.CommentReplayEntity;
import com.study.gmall.service.CommentReplayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 13:55:21
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("/commentreplay")
public class CommentReplayController {
    @Autowired
    private CommentReplayService commentReplayService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority(':commentreplay:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = commentReplayService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority(':commentreplay:info')")
    public Resp<CommentReplayEntity> info(@PathVariable("id") Long id) {
        CommentReplayEntity commentReplay = commentReplayService.getById(id);

        return Resp.ok(commentReplay);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority(':commentreplay:save')")
    public Resp<Object> save(@RequestBody CommentReplayEntity commentReplay) {
        commentReplayService.save(commentReplay);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority(':commentreplay:update')")
    public Resp<Object> update(@RequestBody CommentReplayEntity commentReplay) {
        commentReplayService.updateById(commentReplay);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(':commentreplay:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids) {
        commentReplayService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
