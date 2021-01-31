package com.study.gmall.oms.api;

import com.study.core.bean.Resp;
import com.study.gmall.oms.entity.OrderEntity;
import com.study.gmall.oms.vo.OrderSubmitVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface OmsApi {

    @PostMapping("oms/order")
    Resp<OrderEntity> saveOrder(@RequestBody OrderSubmitVO orderSubmitVO);
}
