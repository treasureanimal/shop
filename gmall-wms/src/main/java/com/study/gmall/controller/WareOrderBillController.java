package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.service.WareOrderBillService;
import com.study.gmall.wms.entity.WareOrderBillEntity;
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
@RequestMapping("wms/wareorderbill")
public class WareOrderBillController {
    @Autowired
    private WareOrderBillService wareOrderBillService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('wms:wareorderbill:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = wareOrderBillService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('wms:wareorderbill:info')")
    public Resp<WareOrderBillEntity> info(@PathVariable("id") Long id){
		WareOrderBillEntity wareOrderBill = wareOrderBillService.getById(id);

        return Resp.ok(wareOrderBill);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('wms:wareorderbill:save')")
    public Resp<Object> save(@RequestBody WareOrderBillEntity wareOrderBill){
		wareOrderBillService.save(wareOrderBill);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('wms:wareorderbill:update')")
    public Resp<Object> update(@RequestBody WareOrderBillEntity wareOrderBill){
		wareOrderBillService.updateById(wareOrderBill);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('wms:wareorderbill:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		wareOrderBillService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
