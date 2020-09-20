package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.sms.entity.CouponSpuEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.CouponSpuService;




/**
 * 优惠券与产品关联
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:31
 */
@Api(tags = "优惠券与产品关联 管理")
@RestController
@RequestMapping("sms/couponspu")
public class CouponSpuController {
    @Autowired
    private CouponSpuService couponSpuService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:couponspu:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = couponSpuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:couponspu:info')")
    public Resp<CouponSpuEntity> info(@PathVariable("id") Long id){
		CouponSpuEntity couponSpu = couponSpuService.getById(id);

        return Resp.ok(couponSpu);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:couponspu:save')")
    public Resp<Object> save(@RequestBody CouponSpuEntity couponSpu){
		couponSpuService.save(couponSpu);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:couponspu:update')")
    public Resp<Object> update(@RequestBody CouponSpuEntity couponSpu){
		couponSpuService.updateById(couponSpu);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:couponspu:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		couponSpuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
