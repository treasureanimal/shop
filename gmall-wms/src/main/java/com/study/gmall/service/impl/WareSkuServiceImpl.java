package com.study.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.gmall.dao.WareSkuDao;
import com.study.gmall.service.WareSkuService;
import com.study.gmall.wms.entity.WareSkuEntity;
import com.study.gmall.wms.vo.SkuLockVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private WareSkuDao wareSkuDao;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "stock:lock:";
    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public String checkAndLockStore(List<SkuLockVO> skuLockVOS) {
        if (CollectionUtils.isEmpty(skuLockVOS)) {
            return "没有选中的商品";
        }

        skuLockVOS.forEach(skuLockVO -> {
            locakStore(skuLockVO);
        });
        List<SkuLockVO> unlockStrore = skuLockVOS.stream().filter(skuLockVO -> skuLockVO.getLock() == false).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(unlockStrore)) {
            //解锁已锁定的商品
            List<SkuLockVO> lockSku = skuLockVOS.stream().filter(SkuLockVO::getLock).collect(Collectors.toList());
            lockSku.forEach(skuLockVO ->{
                this.wareSkuDao.unlockStore(skuLockVO.getSkuId(),skuLockVO.getCount());
            });
            //商品不足锁定失败的商品
            List<Long> unlockSku = unlockStrore.stream().map(SkuLockVO::getSkuId).collect(Collectors.toList());
            return "下单失败，商品库存不足：" + unlockSku.toString();
        }
        String orderToken = skuLockVOS.get(0).getOrderTOken();
        this.redisTemplate.opsForValue().set(KEY_PREFIX+orderToken, JSON.toJSONString(skuLockVOS));
        return null;
    }

    private void locakStore(SkuLockVO skuLockVO){

        RLock lock = this.redissonClient.getLock("stock:" + skuLockVO.getSkuId());
        lock.lock();
        //查询剩余库存够不够获取哪个仓库的商品充足
        List<WareSkuEntity> wareSkuEntities = this.wareSkuDao.checkStore(skuLockVO.getSkuId(),skuLockVO.getCount());
        if (!CollectionUtils.isEmpty(wareSkuEntities)) {
            //锁定库存信息
            Long id = wareSkuEntities.get(0).getId();
            this.wareSkuDao.lockStore(id,skuLockVO.getCount());
            skuLockVO.setLock(true);
            skuLockVO.setSkuId(id);
        }else{
            skuLockVO.setLock(false);
        }
        lock.unlock();
    }
}