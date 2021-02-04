package com.study.gmall.controller;

import com.alipay.api.AlipayApiException;
import com.study.core.bean.Resp;
import com.study.gmall.config.AlipayTemplate;
import com.study.gmall.oms.entity.OrderEntity;
import com.study.gmall.oms.vo.OrderConfirmVO;
import com.study.gmall.oms.vo.OrderSubmitVO;
import com.study.gmall.pay.PayAsyncVo;
import com.study.gmall.pay.PayVo;
import com.study.gmall.service.OrderService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("confirm")
    public Resp<OrderConfirmVO> confirm(){

        OrderConfirmVO confirmVO = this.orderService.confirm();
        return Resp.ok(confirmVO);
    }
    @PostMapping("submit")
    public Resp<OrderEntity> submit(@RequestBody OrderSubmitVO orderSubmitVO){

        OrderEntity orderEntity = this.orderService.submit(orderSubmitVO);
        try {
            PayVo payVo = new PayVo();
            payVo.setOut_trade_no(orderEntity.getOrderSn());
            payVo.setTotal_amount(orderEntity.getPayAmount().toString() != null? orderEntity.getPayAmount().toString():"100");
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
    public Resp<Object> paySuccess(PayAsyncVo payAsyncVo){

        this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","order.pay",payAsyncVo.getOut_trade_no());
        return null;
    }


}
