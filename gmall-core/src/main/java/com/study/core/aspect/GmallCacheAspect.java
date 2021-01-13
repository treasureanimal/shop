package com.study.core.aspect;

import com.alibaba.fastjson.JSON;
import com.study.core.annotation.GmallCache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class GmallCacheAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Around("@annotation(com.study.core.annotation.GmallCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object result = null;
        //获取目标方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();//获取目标方法所在的类中的信息
        Method method = signature.getMethod();
        GmallCache annotation = method.getAnnotation(GmallCache.class);
        //获取目标方法的返回值
        Class<?> returnType = method.getReturnType();
        //获取注解中的缓存前缀
        String prefix = annotation.prefix();
        //从缓存中查询
        String key = prefix + Arrays.asList(args).toString();
        result = cacheHit(key, returnType);
        if (result != null) {
            return result;
        }
        //没有，加分布式锁
        RLock lock = redissonClient.getLock("lock" + Arrays.asList(args).toString());
        lock.lock();
        //再次查询缓存，如果没有执行目标方法
        result = cacheHit(key, returnType);
        if (result != null) {
            lock.unlock(); //释放分布式锁
            return result;
        }
        result = proceedingJoinPoint.proceed();//进入目标方法
        //放入缓存释放分布式锁
        int timeout = annotation.timeout();
        int random = annotation.random();
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(result), timeout + (int) (Math.random() * random), TimeUnit.MINUTES);
        return result;
    }

    private Object cacheHit(String key, Class<?> returnType) {
        String json = stringRedisTemplate.opsForValue().get(key);
        //如果有直接返回
        if (StringUtils.isNotBlank(json)) {
            return JSON.parseObject(json, returnType);
        }
        return null;
    }

}
