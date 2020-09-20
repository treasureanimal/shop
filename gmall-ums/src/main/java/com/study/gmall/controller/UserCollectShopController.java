package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.UserCollectShopEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.UserCollectShopService;




/**
 * 关注店铺表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@Api(tags = "关注店铺表 管理")
@RestController
@RequestMapping("ums/usercollectshop")
public class UserCollectShopController {
    @Autowired
    private UserCollectShopService userCollectShopService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:usercollectshop:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = userCollectShopService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:usercollectshop:info')")
    public Resp<UserCollectShopEntity> info(@PathVariable("id") Long id){
		UserCollectShopEntity userCollectShop = userCollectShopService.getById(id);

        return Resp.ok(userCollectShop);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:usercollectshop:save')")
    public Resp<Object> save(@RequestBody UserCollectShopEntity userCollectShop){
		userCollectShopService.save(userCollectShop);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:usercollectshop:update')")
    public Resp<Object> update(@RequestBody UserCollectShopEntity userCollectShop){
		userCollectShopService.updateById(userCollectShop);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:usercollectshop:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		userCollectShopService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
