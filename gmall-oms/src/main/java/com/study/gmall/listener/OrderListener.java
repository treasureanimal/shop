package com.study.gmall.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.gmall.dao.OrderDao;
import com.study.gmall.oms.entity.OrderEntity;
import com.study.gmall.ums.vo.UserBoundsVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderListener {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitListener(queues = {"ORDER-DEAD-QUEUE"})
    public void closeOrder(String orderToken){

        //如果等于1说明执行了关单操作
        if (this.orderDao.closeOrder(orderToken) == 1) {
            //解锁库存
            this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","stock.unlock",orderToken);

        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ORDER-PAY-QUEUE",durable = "true"),
            exchange = @Exchange(value = "GMALL-ORDER-EXCHANGE",ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"order.pay"}
    ))
    public void payOrder(String orderToken){
        //更新订单状态
        if (orderDao.payOrder(orderToken) == 1) {
            //减库存
            this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","stock.minus",orderToken);
            //加积分
            OrderEntity order = this.orderDao.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderToken));
            UserBoundsVO boundsVO = new UserBoundsVO();
            boundsVO.setIntegration(order.getIntegration());
            boundsVO.setMemberId(order.getMemberId());
            boundsVO.setGrowth(order.getGrowth());
            this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","user.bounds",boundsVO);



        }
    }
}
