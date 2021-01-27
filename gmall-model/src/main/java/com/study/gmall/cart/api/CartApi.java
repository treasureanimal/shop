package com.study.gmall.cart.api;

import com.study.core.bean.Resp;
import com.study.gmall.cart.pojo.Cart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CartApi {

    @GetMapping("cart/{userId}")
    public Resp<List<Cart>> queryCheckCartByUserId(@PathVariable("userId")Long userId);
}
