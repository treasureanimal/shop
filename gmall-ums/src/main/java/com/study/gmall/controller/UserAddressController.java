package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.UserAddressEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.UserAddressService;




/**
 * 收货地址表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Api(tags = "收货地址表 管理")
@RestController
@RequestMapping("ums/useraddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:useraddress:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = userAddressService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:useraddress:info')")
    public Resp<UserAddressEntity> info(@PathVariable("id") Long id){
		UserAddressEntity userAddress = userAddressService.getById(id);

        return Resp.ok(userAddress);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:useraddress:save')")
    public Resp<Object> save(@RequestBody UserAddressEntity userAddress){
		userAddressService.save(userAddress);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:useraddress:update')")
    public Resp<Object> update(@RequestBody UserAddressEntity userAddress){
		userAddressService.updateById(userAddress);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:useraddress:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		userAddressService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
