package com.study.gmall.service;

import com.study.gmall.cart.pojo.Cart;

import java.util.List;

public interface CartService {
    void addCart(Cart cart);

    List<Cart> queryCart();

    void updateCart(Cart cart);

    void deleteCart(Long skuId);
}
