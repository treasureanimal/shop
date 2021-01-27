package com.study.gmall.service.impl;

import com.study.gmall.cart.pojo.UserInfo;
import com.study.gmall.feign.*;
import com.study.gmall.interceptors.LonginInterceptors;
import com.study.gmall.oms.vo.OrderConfirmVO;
import com.study.gmall.service.OrderService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private GmallPmsClientApi pmsClientApi;
    @Autowired
    private GmallWmsClientApi wmsClientApi;
    @Autowired
    private GmallCartClientApi cartClientApi;
    @Autowired
    private GmallSmsClientApi smsClientApi;
    @Autowired
    private GmallUmsClientApi umsClientApi;
    @Autowired
    private GmalOmsClientApi omsClientApi;

    @Override
    public OrderConfirmVO confirm() {

        UserInfo userInfo = LonginInterceptors.getUserInfo();
        Long userId = userInfo.getId();
        if (userId==null) {
            return null;
        }
        //获取用户的收货地址列表,根据用户id查询收货地址
        //获取购物车中选中的商品信息
        //查询用户信息获取积分
        //生成唯一标识，防止重复提交(一份相应到页面，一份保存到redis)

        return null;
    }
}
