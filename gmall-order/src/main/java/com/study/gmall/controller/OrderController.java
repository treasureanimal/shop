package com.study.gmall.controller;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.study.core.bean.Resp;
import com.study.core.exception.OrderException;
import com.study.gmall.config.AlipayTemplate;
import com.study.gmall.oms.entity.OrderEntity;
import com.study.gmall.oms.vo.OrderConfirmVO;
import com.study.gmall.oms.vo.OrderSubmitVO;
import com.study.gmall.pay.PayAsyncVo;
import com.study.gmall.pay.PayVo;
import com.study.gmall.service.OrderService;
import com.study.gmall.wms.vo.SkuLockVO;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayTemplate alipayTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("confirm")
    public Resp<OrderConfirmVO> confirm() {

        OrderConfirmVO confirmVO = this.orderService.confirm();
        return Resp.ok(confirmVO);
    }

    @PostMapping("submit")
    public Resp<OrderEntity> submit(@RequestBody OrderSubmitVO orderSubmitVO) {

        OrderEntity orderEntity = this.orderService.submit(orderSubmitVO);
        try {
            PayVo payVo = new PayVo();
            payVo.setOut_trade_no(orderEntity.getOrderSn());
            payVo.setTotal_amount(orderEntity.getPayAmount().toString() != null ? orderEntity.getPayAmount().toString() : "100");
            payVo.setSubject("谷粒商城");
            payVo.setBody("支付平台");
            String form = this.alipayTemplate.pay(payVo); //返回是支付页面的form表单，展示给用户
            System.out.println("form = " + form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Resp.ok(null);
    }

    @PostMapping("pay/sucess")
    public Resp<Object> paySuccess(PayAsyncVo payAsyncVo) {

        this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE", "order.pay", payAsyncVo.getOut_trade_no());
        return null;
    }

    @PostMapping("seckill/{skuId}")
    public Resp<Object> seckill(@PathVariable("skuId") Long skuId) {

        //使用信号量
        RSemaphore semaphore = this.redissonClient.getSemaphore("semaphore:lock:" + skuId);
        semaphore.trySetPermits(500); //限定请求数
        if (semaphore.tryAcquire()) {


            //获取redis中的库存信息
            String countString = this.redisTemplate.opsForValue().get("order:seckill:" + skuId);
            //没有秒杀结束
            if (StringUtils.isEmpty(countString) || Integer.parseInt(countString) == 0) {
                return Resp.ok("秒杀结束");
            }
            //如果有创建订单并减库存
            Integer count = Integer.parseInt(countString);
            this.redisTemplate.opsForValue().set("order:seckill:" + skuId, (--count).toString());
            //发送消息给消息队列用于减mysql中库存
            SkuLockVO skuLockVO = new SkuLockVO();
            skuLockVO.setCount(1);
            String orderToken = IdWorker.getIdStr();
            skuLockVO.setOrderTOken(orderToken);
            skuLockVO.setSkuId(skuId);
            this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE", "order.seckill", skuLockVO);

            RCountDownLatch countDownLatch = this.redissonClient.getCountDownLatch("count:down:" + orderToken);
            countDownLatch.trySetCount(1);
            countDownLatch.countDown();
            //释放锁
            semaphore.release();
            //相应成功
            return Resp.ok("秒杀成功");
        }
        return Resp.ok("没有抢到商品");
    }

    @GetMapping("seckill/query/{orderToken}")
    public Resp<Object> querySeckill(@PathVariable("orderToken") String orderToken) throws InterruptedException {
        RCountDownLatch countDownLatch = this.redissonClient.getCountDownLatch("count:down:" + orderToken);
        countDownLatch.await();

        //查询订单并相应
        //发送feign请求，查询订单
        return Resp.ok(null);
    }
}
