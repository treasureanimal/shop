package com.study.gmall.service;

import com.study.gmall.pojo.Cart;

import java.util.List;

public interface CartService {
    void addCart(Cart cart);

    List<Cart> queryCart();
}
