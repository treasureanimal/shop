package com.study.gmall.listener;

import com.study.core.bean.Resp;
import com.study.gmall.feign.GmallPmsClient;
import com.study.gmall.feign.GmallWmsClient;
import com.study.gmall.pms.entity.*;
import com.study.gmall.repository.GoodsRepository;
import com.study.gmall.search.pojo.Goods;
import com.study.gmall.search.pojo.SearchAttr;
import com.study.gmall.wms.entity.WareSkuEntity;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@ComponentScan
public class GoodsListener {

    @Autowired
    private GmallPmsClient pmsClient;
    @Autowired
    private GmallWmsClient wmsClient;
    @Autowired
    private GoodsRepository goodsRepository;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "gmall-serch-queue", durable = "true"),
            exchange = @Exchange(value = "PMS-EXCHAGE",type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"), //因为pms是生产消息。所以交换机要和pms一样,交换机有可能有不同的微服务所以需要忽略声明异常
            key = {"item.insert","item.update"}
    ))
    public void listener(Long spuId){
        Resp<List<SkuInfoEntity>> listResp = this.pmsClient.querySkuBySpuId(spuId);
        List<SkuInfoEntity> data = listResp.getData();
        if (!CollectionUtils.isEmpty(data)) {

            //把sku转化成goods对象
            List<Goods> goodsList = data.stream().map(skuInfoEntity -> {
                Goods goods = new Goods();

                //查询搜索属性及值
                Resp<List<ProductAttrValueEntity>> attrValueResp = this.pmsClient.querySearchAttrValueBySpuId(spuId);
                List<ProductAttrValueEntity> attrValueEntities = attrValueResp.getData();
                if (!CollectionUtils.isEmpty(attrValueEntities)) {
                    List<SearchAttr> searchAttrs = attrValueEntities.stream().map(productAttrValueEntity -> {
                        SearchAttr searchAttr = new SearchAttr();
                        searchAttr.setAttrId(productAttrValueEntity.getAttrId());
                        searchAttr.setAttrName(productAttrValueEntity.getAttrName());
                        searchAttr.setAttrValue(productAttrValueEntity.getAttrValue());
                        return searchAttr;
                    }).collect(Collectors.toList());
                    goods.setAttrs(searchAttrs);
                }
                //查询品牌
                Resp<BrandEntity> brandEntityResp = this.pmsClient.queryBrandById(skuInfoEntity.getBrandId());
                BrandEntity brandEntity = brandEntityResp.getData();
                if (brandEntity != null) {
                    goods.setBrandId(skuInfoEntity.getBrandId());
                    goods.setBrandName(brandEntity.getName());
                }

                //查询分类
                Resp<CategoryEntity> categoryEntityResp = this.pmsClient.queryCategoryById(skuInfoEntity.getCatalogId());
                CategoryEntity categoryEntity = categoryEntityResp.getData();
                if (categoryEntity != null) {
                    goods.setCategoryId(skuInfoEntity.getCatalogId());
                    goods.setCategoryName(categoryEntity.getName());
                }
                Resp<SpuInfoEntity> spuInfoEntityResp = this.pmsClient.querySpuById(spuId);
                SpuInfoEntity spuInfoEntity = spuInfoEntityResp.getData();
                if (spuInfoEntity == null) return null;
                goods.setCreateTime(spuInfoEntity.getCreateTime());
                goods.setPic(skuInfoEntity.getSkuDefaultImg());
                goods.setPrice(skuInfoEntity.getPrice().doubleValue());
                goods.setSale(0L);
                goods.setSkuId(skuInfoEntity.getSkuId());

                //查询库存信息
                Resp<List<WareSkuEntity>> wareSkuBySkuId = this.wmsClient.queryWareSkuBySkuId(skuInfoEntity.getSkuId());
                List<WareSkuEntity> wareSkuEntities = wareSkuBySkuId.getData();
                if (!CollectionUtils.isEmpty(wareSkuEntities)) {
                    boolean flag = wareSkuEntities.stream().anyMatch(wareSkuEntity -> wareSkuEntity.getStock() > 0);
                    goods.setStore(flag);
                }
                goods.setTitle(skuInfoEntity.getSkuTitle());

                return goods;
            }).collect(Collectors.toList());
            this.goodsRepository.saveAll(goodsList);
        }

    }
}
