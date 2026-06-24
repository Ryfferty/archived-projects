package com.xspaceagi.system.infra.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {
    /**
     * redis锁的key前缀
     */
    String prefix() default "redis:lock:";
    
    /**
     * redis锁的key，支持SpEL表达式
     */
    String key();
    
    /**
     * 获取锁的等待时间，默认3秒
     */
    long waitTime() default 3;
    
    /**
     * 锁的过期时间，默认30秒
     */
    long leaseTime() default 30;
    
    /**
     * 时间单位，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    
    /**
     * 是否公平锁
     */
    boolean isFair() default false;
} 