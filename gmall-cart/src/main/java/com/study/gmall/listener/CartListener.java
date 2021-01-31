package com.study.gmall.listener;

import com.study.core.bean.Resp;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.pms.entity.SkuInfoEntity;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartListener {

    @Autowired
    private GmallPmsClientApi pmsClientApi;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String PRICE_PREFIX = "gmall:sku";
    private static final String KEY_PREFIX = "gmall:cart";
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "CART-ITEM-QUEUE", durable = "true"),
            exchange = @Exchange(value = "PMS-EXCHAGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.update"}
    ))
    private void lisnter(Long spuId) {
        Resp<List<SkuInfoEntity>> skuInfoResp = this.pmsClientApi.querySkuBySpuId(spuId);
        List<SkuInfoEntity> skuInfoEntities = skuInfoResp.getData();
        skuInfoEntities.forEach(skuInfoEntity -> {
            this.stringRedisTemplate.opsForValue().set(PRICE_PREFIX + skuInfoEntity.getSkuId().toString(), skuInfoEntity.getPrice().toString());
        });
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ORDER-CART-QUEUE",durable = "true"),
            exchange = @Exchange(value = "GMALL-ORDER-EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"cart.delete"}
    ))
    public void deleteLisnter(Map<String,Object> map){
        Long userId = (Long)map.get("userId");
        List<Object> skuIds = (List<Object>) map.get("skuIds");
        BoundHashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.boundHashOps(PRICE_PREFIX + userId);
        List<String> skus = skuIds.stream().map(skuId -> skuId.toString()).collect(Collectors.toList());
        hashOps.delete(skuIds.toArray(new String[skus.size()]));
    }
}
