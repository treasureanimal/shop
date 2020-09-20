package com.study.gmall.controller;

import java.util.Arrays;


import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.SpuAttrValueEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.study.gmall.service.SpuAttrValueService;




/**
 * spu属性值
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Api(tags = "spu属性值 管理")
@RestController
@RequestMapping("pms/spuattrvalue")
public class SpuAttrValueController {
    @Autowired
    private SpuAttrValueService spuAttrValueService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:spuattrvalue:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = spuAttrValueService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:spuattrvalue:info')")
    public Resp<SpuAttrValueEntity> info(@PathVariable("id") Long id){
		SpuAttrValueEntity spuAttrValue = spuAttrValueService.getById(id);

        return Resp.ok(spuAttrValue);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spuattrvalue:save')")
    public Resp<Object> save(@RequestBody SpuAttrValueEntity spuAttrValue){
		spuAttrValueService.save(spuAttrValue);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:spuattrvalue:update')")
    public Resp<Object> update(@RequestBody SpuAttrValueEntity spuAttrValue){
		spuAttrValueService.updateById(spuAttrValue);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:spuattrvalue:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		spuAttrValueService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
