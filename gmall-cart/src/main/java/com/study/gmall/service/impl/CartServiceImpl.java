package com.study.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.study.core.bean.Resp;
import com.study.gmall.cart.pojo.UserInfo;
import com.study.gmall.cart.pojo.Cart;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.feign.GmallSmsClientApi;
import com.study.gmall.feign.GmallWmsClientApi;
import com.study.gmall.interceptors.LonginInterceptors;
import com.study.gmall.pms.entity.SkuInfoEntity;
import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.study.gmall.service.CartService;
import com.study.gmall.sms.vo.SaleVO;
import com.study.gmall.wms.entity.WareSkuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static final String KEY_PREFIX = "gmall:cart";
    private static final String PRICE_PREFIX = "gmall:sku";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private GmallPmsClientApi pmsClientApi;
    @Autowired
    private GmallSmsClientApi smsClientApi;
    @Autowired
    private GmallWmsClientApi wmsClientApi;

    @Override
    public void addCart(Cart cart) {
        String key = getLongStatus();
        //set user skuId value key就是user，获取该用户下购物车中所有商品
        BoundHashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.boundHashOps(key);

        String skuId = cart.getSkuId().toString();
        Integer count = cart.getCount();

        //判断购物车中是否有记录
        if (hashOps.hasKey(skuId)) {
            //如果有则更新数量
            //获取购物车中的sku记录
            String cartJson = hashOps.get(skuId).toString();
            cart = JSON.parseObject(cartJson, Cart.class);
            //更新商品的数量
            cart.setCount(cart.getCount() + count);
            //放到redis中
            hashOps.put(skuId, JSON.toJSONString(cart));
        } else {
            //如果没有新增购物车
            //默认被勾选
            cart.setCheck(true);
            //查询sku相关信息
            Resp<SkuInfoEntity> skuInfoEntityResp = pmsClientApi.querySkuById(cart.getSkuId());
            SkuInfoEntity skuInfoEntity = skuInfoEntityResp.getData();

            if (skuInfoEntity == null) {
                return;
            }
            cart.setDefaultImage(skuInfoEntity.getSkuDefaultImg());
            cart.setPrice(skuInfoEntity.getPrice());
            cart.setTitle(skuInfoEntity.getSkuTitle());
            //查询营销属性
            Resp<List<SkuSaleAttrValueEntity>> skuSaleResp = pmsClientApi.querySkuSaleAttrValuesBySkuId(cart.getSkuId());
            List<SkuSaleAttrValueEntity> skuSale = skuSaleResp.getData();
            cart.setSaleAttrValues(skuSale);

            // 查询营销信息
            Resp<List<SaleVO>> saleResp = this.smsClientApi.querySalesBySkuId(cart.getSkuId());
            List<SaleVO> saleVOS = saleResp.getData();
            cart.setSales(saleVOS);
            // 查询库存信息
            Resp<List<WareSkuEntity>> wareResp = this.wmsClientApi.queryWareSkuBySkuId(cart.getSkuId());
            List<WareSkuEntity> wareSkuEntities = wareResp.getData();
            if (!CollectionUtils.isEmpty(wareSkuEntities)) {
                cart.setStore(wareSkuEntities.stream().anyMatch(wareSkuEntity -> wareSkuEntity.getStock() > 0));
            }
            this.stringRedisTemplate.opsForValue().set(PRICE_PREFIX + skuId, skuInfoEntity.getPrice().toString());
        }
        hashOps.put(skuId, JSON.toJSONString(cart));
    }

    @Override
    public List<Cart> queryCart() {

        //获取登录状态
        UserInfo userInfo = LonginInterceptors.getUserInfo();

        //查询未登录的购物车
        String unLogin = KEY_PREFIX + userInfo.getUserKey();
        BoundHashOperations<String, Object, Object> unLoginHashops = this.stringRedisTemplate.boundHashOps(unLogin);
        List<Object> cartList = unLoginHashops.values();
        List<Cart> unLoginCarts = null;
        if (!CollectionUtils.isEmpty(cartList)) {
            unLoginCarts = cartList.stream().map(cart -> {
                Cart cart1 = JSON.parseObject(cart.toString(), Cart.class);
                //查询当前价格
                String priceString = this.stringRedisTemplate.opsForValue().get(PRICE_PREFIX + cart1.getSkuId());
                cart1.setCurrentPrice(new BigDecimal(priceString));
                return cart1;
            }).collect(Collectors.toList());
        }
        //判断是否登录，如果未登录直接返回
        if (userInfo.getId() == null) {
            return unLoginCarts;
        }
        //登录，购物车同步
        String loginKey = KEY_PREFIX + userInfo.getId();
        BoundHashOperations<String, Object, Object> loginHashops = this.stringRedisTemplate.boundHashOps(loginKey);

        if (!CollectionUtils.isEmpty(unLoginCarts)) {
            unLoginCarts.forEach(cart -> {
                Integer count = cart.getCount();
                if (loginHashops.hasKey(cart.getSkuId().toString())) {
                    String cartJson = loginHashops.get(cart.getSkuId().toString()).toString();
                    cart = JSON.parseObject(cartJson, Cart.class);
                    cart.setCount(cart.getCount() + count);
                }
                loginHashops.put(cart.getSkuId().toString(), JSON.toJSONString(cart));
            });

            //同步完之后删除未登录的购物车商品
            this.stringRedisTemplate.delete(unLogin);
        }

        //查询登录状态的购物车
        List<Object> loginCarts = loginHashops.values();
        return loginCarts.stream().map(cartJson ->
        {
            Cart cart = JSON.parseObject(cartJson.toString(), Cart.class);
            //查询当前价格
            String priceString = this.stringRedisTemplate.opsForValue().get(PRICE_PREFIX + cart.getSkuId());
            cart.setCurrentPrice(new BigDecimal(priceString));
            return cart;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateCart(Cart cart) {
        String key = this.getLongStatus();
        //获取购物车
        BoundHashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.boundHashOps(key);
        Integer count = cart.getCount();

        //判断更新的这条记录，在购物车中有没有
        if(hashOps.hasKey(cart.getSkuId().toString())){
            String cartJson =hashOps.get(cart.getSkuId().toString()).toString();
            cart = JSON.parseObject(cartJson, Cart.class);
            cart.setCount(count);
            hashOps.put(cart.getSkuId().toString(),JSON.toJSONString(cart));
        }
    }

    @Override
    public void deleteCart(Long skuId) {
        String key = this.getLongStatus();
        BoundHashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.boundHashOps(key);
        if (hashOps.hasKey(skuId.toString())) {
            hashOps.delete(skuId.toString());
        }
    }

    @Override
    public List<Cart> queryCheckCartByUserId(Long userId) {
        BoundHashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.boundHashOps(KEY_PREFIX + userId);
        List<Object> cartJson = hashOps.values();
        return cartJson.stream().map(cart ->JSON.parseObject(cart.toString(),Cart.class))
                .filter(Cart::getCheck).collect(Collectors.toList());
    }


    private String getLongStatus() {
        String key = KEY_PREFIX;
        //获取登录状态
        UserInfo userInfo = LonginInterceptors.getUserInfo();
        if (userInfo.getId() != null) {
            key += userInfo.getId();
        } else {
            key += userInfo.getUserKey();
        }
        return key;
    }
}
