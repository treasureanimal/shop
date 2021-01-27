package com.study.gmall.cart.pojo;

import lombok.Data;

@Data
public class UserInfo {

    private String userKey;  //未登录时是userKey保存cookie中
    private Long id;   //登录后是Id 通过id来查找购物车
}