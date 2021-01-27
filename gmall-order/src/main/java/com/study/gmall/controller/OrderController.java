package com.study.gmall.controller;

import com.study.core.bean.Resp;
import com.study.gmall.oms.vo.OrderConfirmVO;
import com.study.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public Resp<OrderConfirmVO> confirm(){

        OrderConfirmVO confirmVO = this.orderService.confirm();
        return Resp.ok(confirmVO);
    }
}
