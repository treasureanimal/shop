package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.sms.entity.SeckillSkuEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.SeckillSkuService;




/**
 * 秒杀活动商品关联
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:31
 */
@Api(tags = "秒杀活动商品关联 管理")
@RestController
@RequestMapping("sms/seckillsku")
public class SeckillSkuController {
    @Autowired
    private SeckillSkuService seckillSkuService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:seckillsku:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = seckillSkuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:seckillsku:info')")
    public Resp<SeckillSkuEntity> info(@PathVariable("id") Long id){
		SeckillSkuEntity seckillSku = seckillSkuService.getById(id);

        return Resp.ok(seckillSku);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:seckillsku:save')")
    public Resp<Object> save(@RequestBody SeckillSkuEntity seckillSku){
		seckillSkuService.save(seckillSku);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:seckillsku:update')")
    public Resp<Object> update(@RequestBody SeckillSkuEntity seckillSku){
		seckillSkuService.updateById(seckillSku);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:seckillsku:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		seckillSkuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
