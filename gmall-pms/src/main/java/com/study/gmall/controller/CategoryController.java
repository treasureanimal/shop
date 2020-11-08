package com.study.gmall.controller;

import java.util.Arrays;
import java.util.List;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.CategoryEntity;
import com.study.gmall.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 商品三级分类
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@Api(tags = "商品三级分类 管理")
@RestController
@RequestMapping("pms/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     *查询商品分类三级分类
     * @param level 层级0是第一层
     * @param parentCId 父类id
     * @return 返回值
     */
    @GetMapping
    public Resp<List<CategoryEntity>> querycategory(@RequestParam(value = "level",defaultValue = "0") Integer level,
                                              @RequestParam(value = "parentCId",required = false)Long parentCId){
        QueryWrapper<CategoryEntity> queryWrapper = new QueryWrapper<>();
        if (level != 0) {
            queryWrapper.eq("cat_level",level);
        }
        if (parentCId !=null) {
            queryWrapper.eq("parent_cid",parentCId);
        }
        List<CategoryEntity> list = categoryService.list(queryWrapper);
        return Resp.ok(list);
    }
    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:category:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = categoryService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:category:info')")
    public Resp<CategoryEntity> info(@PathVariable("id") Long id){
		CategoryEntity category = categoryService.getById(id);

        return Resp.ok(category);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:category:save')")
    public Resp<Object> save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:category:update')")
    public Resp<Object> update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:category:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		categoryService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
