package com.study.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;

import java.lang.annotation.*;

@Target({ElementType.METHOD}) //这个注解作用在哪个地方(类上还是方法上)
@Retention(RetentionPolicy.RUNTIME)//运行时注解还是编译时注解
@Documented//是否要加入文档中
public @interface GmallCache {

    /**
     * value和prefix是一样的如果是value可以直接省略 eg.("") == (prefix="")
     * 让用户指定一个缓存key前缀
     */
    @AliasFor("prefix")
    String value() default "";

    @AliasFor("value")
    String prefix() default "";

    /**
     * 缓存过期时间
     * 单位为分钟
     */
    int timeout() default 5;

    /**
     *  为防止雪崩，设置的随机值范围
     *
     */
    int random() default 5;
}
