package com.study.gmall.service.impl;

import com.study.core.bean.Resp;
import com.study.core.thread.ThreadPoolConfig;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.feign.GmallSmsClientApi;
import com.study.gmall.feign.GmallWmsClientApi;
import com.study.gmall.item.ItemGroupVO;
import com.study.gmall.item.ItemVO;
import com.study.gmall.pms.entity.*;
import com.study.gmall.service.ItemService;
import com.study.gmall.sms.vo.SaleVO;
import com.study.gmall.wms.entity.WareSkuEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;


public class ItemServiceImpl implements ItemService {

    @Autowired
    private GmallSmsClientApi gmallSmsClientApi;
    @Autowired
    private GmallPmsClientApi gmallPmsClientApi;
    @Autowired
    private GmallWmsClientApi gmallWmsClientApi;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public ItemVO queryItemVO(Long skuId) {
        ItemVO itemVO = new ItemVO();

        //设置sku属性
        itemVO.setSkuId(skuId);

        //根据Id查sku
        CompletableFuture<Object> skuCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Resp<SkuInfoEntity> skuInfoEntityResp = this.gmallPmsClientApi.querySkuById(skuId);
            SkuInfoEntity data = skuInfoEntityResp.getData();
            if (data == null) {
                return itemVO;
            }
            itemVO.setSkuTitle(data.getSkuTitle());
            itemVO.setSubTitle(data.getSkuSubtitle());
            itemVO.setPrice(data.getPrice());
            itemVO.setWeight(data.getWeight());
            itemVO.setSpuId(data.getSpuId());
            return data;
        }, threadPoolExecutor);

        CompletableFuture<Void> spuCompletableFuture = skuCompletableFuture.thenAcceptAsync(data -> {
            //根据sku中的skuId查询spu
            Resp<SpuInfoEntity> spuInfoEntityResp = this.gmallPmsClientApi.querySpuById(((SkuInfoEntity) data).getSpuId());
            SpuInfoEntity spuInfoEntity = spuInfoEntityResp.getData();

            if (spuInfoEntity != null) {
                itemVO.setSpuName(spuInfoEntity.getSpuName());
            }
        }, threadPoolExecutor);

        //根据skuId查询图片列表
        CompletableFuture<Void> picsCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<List<SkuImagesEntity>> images = this.gmallPmsClientApi.querySkuImagesBySkuId(skuId);
            List<SkuImagesEntity> imagesData = images.getData();
            itemVO.setPics(imagesData);
        }, threadPoolExecutor);

        //根据sku中的brandId和CategoryId查询品牌和分类
        CompletableFuture<Void> brandCompletableFuture = skuCompletableFuture.thenAcceptAsync(data -> {
            Resp<BrandEntity> brandEntityResp = this.gmallPmsClientApi.queryBrandById(((SkuInfoEntity) data).getBrandId());
            BrandEntity brandEntity = brandEntityResp.getData();
            itemVO.setBrandEntity(brandEntity);
        }, threadPoolExecutor);

        CompletableFuture<Void> categoryCompletableFuture = skuCompletableFuture.thenAcceptAsync(data -> {
            Resp<CategoryEntity> categoryEntityResp = this.gmallPmsClientApi.queryCategoryById(((SkuInfoEntity) data).getCatalogId());
            CategoryEntity categoryEntity = categoryEntityResp.getData();
            itemVO.setCategoryEntity(categoryEntity);
        }, threadPoolExecutor);

        //根据sku查询营销信息
        CompletableFuture<Void> saleCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<List<SaleVO>> saleResp = this.gmallSmsClientApi.querySalesBySkuId(skuId);
            List<SaleVO> saleVOS = saleResp.getData();
            itemVO.setSales(saleVOS);
        },threadPoolExecutor);

        //根据sku查询库存信息
        CompletableFuture<Void> storeCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<List<WareSkuEntity>> wareResp = this.gmallWmsClientApi.queryWareSkuBySkuId(skuId);
            List<WareSkuEntity> wareSkuEntityList = wareResp.getData();
            itemVO.setStore(wareSkuEntityList.stream().anyMatch(wareSkuEntity -> wareSkuEntity.getStock() > 0));
        }, threadPoolExecutor);

        //根据spuId查询所有的skuIds，再去查询所有的销售属性
        CompletableFuture<Void> saleAttrCompletableFuture = skuCompletableFuture.thenAcceptAsync(data -> {
            Resp<List<SkuSaleAttrValueEntity>> skuSaleAttrValueResp = this.gmallPmsClientApi.querySkuSaleAttrValuesBySpuId(((SkuInfoEntity) data).getSpuId());
            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = skuSaleAttrValueResp.getData();
            itemVO.setSaleAttrs(skuSaleAttrValueEntities);
        }, threadPoolExecutor);

        //根据spuId查询商品描述（海报）
        CompletableFuture<Void> descCompletableFuture = skuCompletableFuture.thenAcceptAsync(data -> {
            Resp<SpuInfoDescEntity> spuInfoDescEntityResp = this.gmallPmsClientApi.querySpuDescBySpuId(((SkuInfoEntity) data).getSpuId());
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescEntityResp.getData();
            if (spuInfoDescEntity != null) {
                String decript = spuInfoDescEntity.getDecript();
                String[] split = StringUtils.split(decript, ",");
                itemVO.setImages(Arrays.asList(split));
            }
        },threadPoolExecutor);

        //根据spuId和cateId查询组及组下规格参数（带值）
        CompletableFuture<Void> groupCompletableFuture = skuCompletableFuture.thenAcceptAsync(data -> {
            Resp<List<ItemGroupVO>> itemGroupVoResp = this.gmallPmsClientApi.queryItemGroupVOByCidAndSpuId(((SkuInfoEntity) data).getCatalogId(), ((SkuInfoEntity) data).getSpuId());
            List<ItemGroupVO> itemGroupVOS = itemGroupVoResp.getData();
            itemVO.setGroups(itemGroupVOS);
        },threadPoolExecutor);

        /**
         *为了防止主线程执行完，子线程还没结束就直接return所以需要阻断
         */
        CompletableFuture.allOf(spuCompletableFuture,picsCompletableFuture,brandCompletableFuture,
                                categoryCompletableFuture,saleCompletableFuture,storeCompletableFuture,
                                saleAttrCompletableFuture,descCompletableFuture,groupCompletableFuture).join();
        return itemVO;
    }
}
