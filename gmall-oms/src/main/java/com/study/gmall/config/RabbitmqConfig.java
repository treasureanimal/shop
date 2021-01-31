package com.study.gmall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    /**
     * 延时队列
     * @return
     */
    @Bean("ORDER-TTL-QUEUE")
    public Queue ttlqueue(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "GMALL-ORDER-EXCHANGE");//死信交换机，消息发送的交换机
        map.put("x-dead-letter-routing-key", "order.dead"); //死信路由，交换机和延时队列路由
        map.put("x-message-ttl", 1200000);//延时队列的过期时间
        return new Queue("ORDER-TTL-QUEUE",true,false,false,map);
    }

    /**
     * 延时队列和交换机的绑定
     * @return
     */
    @Bean("ORDER-TTL-BINDING")
    public Binding ttlBinding(){

        return new Binding("ORDER-TTL-QUEUE", Binding.DestinationType.QUEUE, "GMALL-ORDER-EXCHANGE", "order.ttl", null);
    }

    /**
     * 死信队列，
     * @return
     */
    @Bean("ORDER-DEAD-QUEUE")
    public Queue dlQueue(){
        return new Queue("ORDER-DEAD-QUEUE", true, false, false, null);
    }

    /**
     * 死信队列和死信交换机的绑定
     * @return
     */
    @Bean("ORDER-DEAD-BINDING")
    public Binding deadBinding(){

        return new Binding("ORDER-DEAD-QUEUE", Binding.DestinationType.QUEUE, "GMALL-ORDER-EXCHANGE", "order.dead", null);
    }

}
