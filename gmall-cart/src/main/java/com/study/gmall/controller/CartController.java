package com.study.gmall.controller;


import com.study.core.bean.Resp;
import com.study.gmall.cart.pojo.Cart;
import com.study.gmall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 使用redis中的hash进行对购物车的操作
 * set key field value == Map<key,Map<field,value>>都为String类型
 * key为用户id（未登录和已登录）
 * field为商品skuId
 * value为商品的具体信息（价格，数量图片等）
 */
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

    @GetMapping
    public Resp<List<Cart>> queryCarts(){
        List<Cart> carts = this.cartService.queryCart();
        return Resp.ok(carts);
    }

    /**
     * 更新购物车
     */
    @PostMapping("/update")
    public Resp<Object> updateCart(@RequestBody Cart cart){
        this.cartService.updateCart(cart);
        return Resp.ok(null);
    }

    /**
     *删除购物车
     */
    @PostMapping("delete/{skuId}")
    public Resp<Object> deleteCart(@PathVariable("skuId") Long skuId){
        this.cartService.deleteCart(skuId);
        return Resp.ok(null);
    }
}
