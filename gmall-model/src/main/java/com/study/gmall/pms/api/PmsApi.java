package com.study.gmall.pms.api;

import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.*;
import com.study.gmall.pms.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PmsApi {

    @PostMapping("pms/spuinfo/page")
    Resp<List<SpuInfoEntity>> querySpuPage(@RequestBody QueryCondition queryCondition);
    /**
     * 分页查询(排序)
     */
    @GetMapping("pms/spuinfo/list")
    Resp<PageVo> list(QueryCondition queryCondition);

    @GetMapping("pms/skuinfo/{spuId}")
    Resp<List<SkuInfoEntity>> querySkuBySpuId(@PathVariable("spuId")Long spuId);


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("pms/skuinfo/info/{skuId}")
    Resp<SkuInfoEntity> querySkuById(@PathVariable("skuId") Long skuId);
    /**
     * 根据品牌id查询品牌
     * @param brandId 品牌id
     * @return Resp.ok
     */
    @GetMapping("pms/brand/info/{brandId}")
    Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);

    /**
     * 根据分类id查询分类
     * @param catId 分类id
     * @return Resp.ok
     */
    @GetMapping("pms/category/info/{catId}")
    Resp<CategoryEntity> queryCategoryById(@PathVariable("catId") Long catId);

    /**
     * 根据spuId查询商品对应的搜索属性及值
     * @param spuId 商品id
     * @return Resp.ok
     */
    @GetMapping("pms/productattrvalue/{spuId}")
    Resp<List<ProductAttrValueEntity>> querySearchAttrValueBySpuId(@PathVariable("spuId") Long spuId);

    @GetMapping("pms/spuinfo/info/{id}")
    Resp<SpuInfoEntity> querySpuById(@PathVariable("id") Long id);

    @GetMapping("pms/category")
    Resp<List<CategoryEntity>> queryCategory(@RequestParam(value="level", defaultValue = "0")Integer level,
                                                    @RequestParam(value="parentCid", required = false)Long parentCid);
    @GetMapping("pms/category/{pid}")
    Resp<List<CategoryVO>> querySubCategories(@PathVariable("pid") Long pid);

    @GetMapping("pms/skuimages/{skuId}")
    public Resp<List<SkuImagesEntity>> querySkuImagesBySkuId(@PathVariable("skuId") Long skuId);
}
