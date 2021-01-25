package com.study.gmall.controller;


import com.study.core.bean.Resp;
import com.study.gmall.pojo.Cart;
import com.study.gmall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public Resp<Object> addCart(@RequestBody Cart cart){
        this.cartService.addCart(cart);
        return Resp.ok(null);
    }
}
