package com.study.gmall.service;

import com.study.gmall.oms.vo.OrderConfirmVO;
import com.study.gmall.oms.vo.OrderSubmitVO;

public interface OrderService {
    OrderConfirmVO confirm();

    void submit(OrderSubmitVO orderSubmitVO);
}
