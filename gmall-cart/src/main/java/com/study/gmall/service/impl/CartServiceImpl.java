package com.study.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.core.bean.Resp;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.feign.GmallSmsClientApi;
import com.study.gmall.feign.GmallWmsClientApi;
import com.study.gmall.interceptors.LonginInterceptors;
import com.study.gmall.pms.entity.SkuInfoEntity;
import com.study.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.study.gmall.pojo.Cart;
import com.study.gmall.pojo.UserInfo;
import com.study.gmall.service.CartService;
import com.study.gmall.sms.vo.SaleVO;
import com.study.gmall.wms.entity.WareSkuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private static final String KEY_PREFIX = "gmall:cart";

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
                return ;
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
        }
        hashOps.put(skuId, JSON.toJSONString(cart));
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
