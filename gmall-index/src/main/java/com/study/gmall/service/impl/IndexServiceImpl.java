package com.study.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.study.core.annotation.GmallCache;
import com.study.core.bean.Resp;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.pms.entity.CategoryEntity;
import com.study.gmall.pms.vo.CategoryVO;
import com.study.gmall.service.IndexService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class IndexServiceImpl implements IndexService {

    /**
     * jdk序列化：速度最快，内存占用比较高，可读性差
     * json和jackson序列化：速度慢，因为有括号等字符所以占用内存高
     * xml序列化：速度慢，占用内存高
     * String序列化：比jdk慢，比json快占用内存就是字符大小，比较合适
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//默认设置好的String序列化工具，stringRedisTemplate默认是jdk序列化工具，需要手动去设置
    @Autowired
    private GmallPmsClientApi gmallPmsClientApi;
    @Autowired
    private RedissonClient redissonClient;

    private static final String KEY_PREFIX = "index:cates:"; //加前缀为了区分后期购物车也有相同的key，而且这个前缀会成为树状文件夹
    @Override
    public List<CategoryEntity> queryLV1CateGories() {
        Resp<List<CategoryEntity>> listResp = this.gmallPmsClientApi.queryCategory(1, null);
        return listResp.getData();
    }

    @Override
    @GmallCache(value = KEY_PREFIX,timeout = 7200,random = 100)
    public List<CategoryVO> querySubCategories(Long pid) {
        //1.判断缓存中有没有
        String cateJson = this.stringRedisTemplate.opsForValue().get(KEY_PREFIX + pid);//key必须唯一
        //2.如果有直接返回，如果没有数据库中查询放入缓存
        if (!StringUtils.isEmpty(cateJson)) {
            List<CategoryVO> categoryVOS = JSON.parseArray(cateJson, CategoryVO.class);
        }

        /**
         * 如果有A,B两个线程查询缓存没有数据,A线程进行加锁重新查一遍缓存，此时也没有数据，从数据库中进行查询，此时B线程等待
         * 当A从数据库中查询出数据，将数据写入缓存。此时B线程进入锁，然后重新查询缓存此时缓存中有数据，释放锁进行返回
         * 下边注释掉的操作，通过aop和自定义注解对其功能进行替换
         */
//        RLock lock = redissonClient.getLock("lock" + pid);
//        lock.lock();
//        1.判断缓存中有没有锁，
//        String cateJson1 = this.stringRedisTemplate.opsForValue().get(KEY_PREFIX + pid);//key必须唯一
//        2.如果有直接返回，如果没有数据库中查询放入缓存
//        if (!StringUtils.isEmpty(cateJson1)) {
//            lock.unlock();
//            List<CategoryVO> categoryVOS = JSON.parseArray(cateJson, CategoryVO.class);
//        }
        //查询数据库
        Resp<List<CategoryVO>> listResp = gmallPmsClientApi.querySubCategories(pid);
        List<CategoryVO> categoryVOS = listResp.getData();
        //3.查询完成后放入缓存
//        stringRedisTemplate.opsForValue().set(KEY_PREFIX + pid,JSON.toJSONString(categoryVOS),7, TimeUnit.DAYS);
//        lock.unlock();
        return listResp.getData();
    }

    /**
     * 锁
     * 1. 互斥性
     * 2. 获取锁并设置过期时间防止死锁，要具备原子性。释放锁也要具备原子性
     * 3. 解铃还须系铃人
     */
    public void testLock() {

        RLock lock = this.redissonClient.getLock("lock");
        lock.lock();

        String numString = this.stringRedisTemplate.opsForValue().get("num");
        if (StringUtils.isEmpty(numString)) {
            return;
        }
        int num = Integer.parseInt(numString);
        this.stringRedisTemplate.opsForValue().set("num", String.valueOf(++num));

        lock.unlock();
    }

    /**
     * 锁
     * 1. 互斥性
     * 2. 获取锁并设置过期时间防止死锁，要具备原子性。释放锁也要具备原子性
     * 3. 解铃还须系铃人
     */
    public void testLock1() {

        // 给自己的锁生成一个唯一标志
        String uuid = UUID.randomUUID().toString();
        // 执行redis的setnx命令
        Boolean lock = this.stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 5, TimeUnit.SECONDS);

        // 判断是否拿到锁
        if (lock) {

            String numString = this.stringRedisTemplate.opsForValue().get("num");
            if (StringUtils.isEmpty(numString)) {
                return;
            }
            int num = Integer.parseInt(numString);
            this.stringRedisTemplate.opsForValue().set("num", String.valueOf(++num));

            // 释放锁资源，其他请求才能执行（LUA）
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            this.stringRedisTemplate.execute(new DefaultRedisScript<>(script), Arrays.asList("lock"), uuid);
//            if(StringUtils.equals(this.stringRedisTemplate.opsForValue().get("lock"), uuid)) {
//                this.stringRedisTemplate.delete("lock");
//            }
        } else {
            // 其他请求重试获取锁
            testLock();
        }
    }

    public String testRead() {
        RReadWriteLock rwLock = this.redissonClient.getReadWriteLock("rwLock");
        rwLock.readLock().lock(10l, TimeUnit.SECONDS);

        String test = this.stringRedisTemplate.opsForValue().get("test");

//        rwLock.readLock().unlock();
        return test;
    }

    public String testWrite() {
        RReadWriteLock rwLock = this.redissonClient.getReadWriteLock("rwLock");
        rwLock.writeLock().lock(10l, TimeUnit.SECONDS);

        this.stringRedisTemplate.opsForValue().set("test", UUID.randomUUID().toString());

//        rwLock.writeLock().unlock();

        return "写入了数据";
    }

    public String testLatch() throws InterruptedException {
        RCountDownLatch latch = this.redissonClient.getCountDownLatch("latch");
        latch.trySetCount(5);

        latch.await();
        return "主业务开始执行。。。。。";
    }

    public String testCountdown() {
        RCountDownLatch latch = this.redissonClient.getCountDownLatch("latch");
        latch.countDown();

        return "分支业务执行了一次";
    }
}
