package com.study.gmall.listener;

import com.alibaba.fastjson.JSON;
import com.study.gmall.dao.WareSkuDao;
import com.study.gmall.wms.vo.SkuLockVO;
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
public class WareListener {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WareSkuDao wareSkuDao;

    private static final String KEY_PREFIX = "stock:lock:";

    /**
     * 库存监听订单发送解锁库存的消息，进行消费
     *
     * @param orderToken
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "WMS-UNLOCK-QUEUE", durable = "true"),
            exchange = @Exchange(value = "GMALL-ORDER-EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"stock.unloc"}))
    public void unlockListener(String orderToken) {
        String lockJson = this.redisTemplate.opsForValue().get(KEY_PREFIX + orderToken);
        List<SkuLockVO> skuLockVOS = JSON.parseArray(lockJson, SkuLockVO.class);
        skuLockVOS.forEach(skuLockVO -> {
            this.wareSkuDao.unlockStore(skuLockVO.getWareSkuId(), skuLockVO.getCount());
        });


    }
}
