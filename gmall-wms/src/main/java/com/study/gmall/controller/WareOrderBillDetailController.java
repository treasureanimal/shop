package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.service.WareOrderBillDetailService;
import com.study.gmall.wms.entity.WareOrderBillDetailEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



/**
 * 库存工作单
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:35:39
 */
@Api(tags = "库存工作单 管理")
@RestController
@RequestMapping("wms/wareorderbilldetail")
public class WareOrderBillDetailController {
    @Autowired
    private WareOrderBillDetailService wareOrderBillDetailService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('wms:wareorderbilldetail:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = wareOrderBillDetailService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('wms:wareorderbilldetail:info')")
    public Resp<WareOrderBillDetailEntity> info(@PathVariable("id") Long id){
		WareOrderBillDetailEntity wareOrderBillDetail = wareOrderBillDetailService.getById(id);

        return Resp.ok(wareOrderBillDetail);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('wms:wareorderbilldetail:save')")
    public Resp<Object> save(@RequestBody WareOrderBillDetailEntity wareOrderBillDetail){
		wareOrderBillDetailService.save(wareOrderBillDetail);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('wms:wareorderbilldetail:update')")
    public Resp<Object> update(@RequestBody WareOrderBillDetailEntity wareOrderBillDetail){
		wareOrderBillDetailService.updateById(wareOrderBillDetail);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('wms:wareorderbilldetail:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		wareOrderBillDetailService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
