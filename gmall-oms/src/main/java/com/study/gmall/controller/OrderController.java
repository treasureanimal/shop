package com.study.gmall.controller;

import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.oms.entity.OrderEntity;
import com.study.gmall.oms.vo.OrderSubmitVO;
import com.study.gmall.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;




/**
 * 
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 20:33:58
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("oms/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:order:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = orderService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:order:info')")
    public Resp<OrderEntity> info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);

        return Resp.ok(order);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:order:save')")
    public Resp<Object> save(@RequestBody OrderEntity order){
		orderService.save(order);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:order:update')")
    public Resp<Object> update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:order:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

    @PostMapping
    public Resp<OrderEntity> saveOrder(@RequestBody OrderSubmitVO orderSubmitVO){
        OrderEntity orderEntity = this.orderService.saveOrder(orderSubmitVO);
        return Resp.ok(orderEntity);
    }
}
