package com.study.gmall.listener;

import com.study.core.bean.Resp;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.pms.entity.SkuInfoEntity;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartListener {

    @Autowired
    private GmallPmsClientApi pmsClientApi;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String PRICE_PREFIX = "gmall:sku";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "CART-ITEM-QUEUE",durable = "true"),
            exchange = @Exchange(value = "PMS-EXCHAGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.update"}
    ))
    private void lisnter(Long spuId){
        Resp<List<SkuInfoEntity>> skuInfoResp = this.pmsClientApi.querySkuBySpuId(spuId);
        List<SkuInfoEntity> skuInfoEntities = skuInfoResp.getData();
        skuInfoEntities.forEach(skuInfoEntity -> {
    this.stringRedisTemplate.opsForValue().set(PRICE_PREFIX + skuInfoEntity.getSkuId().toString(),skuInfoEntity.getPrice().toString());
        });
    }
}
