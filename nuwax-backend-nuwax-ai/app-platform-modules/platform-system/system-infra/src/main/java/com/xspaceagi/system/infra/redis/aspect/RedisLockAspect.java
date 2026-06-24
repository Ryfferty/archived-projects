package com.xspaceagi.system.infra.redis.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.xspaceagi.system.infra.redis.annotation.RedisLock;

import java.lang.reflect.Method;

@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        String lockKey = getLockKey(joinPoint, redisLock);
        RLock lock = redissonClient.getLock(lockKey);
        
        boolean locked = false;
        try {
            // 尝试获取锁
            locked = lock.tryLock(redisLock.waitTime(), redisLock.leaseTime(), redisLock.timeUnit());
            if (!locked) {
                throw new RuntimeException("获取锁失败");
            }
            return joinPoint.proceed();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    private String getLockKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 获取SpEL表达式
        String key = redisLock.key();
        
        // 创建解析器
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        
        // 设置解析上下文
        EvaluationContext context = new StandardEvaluationContext();
        
        // 获取参数名和参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        
        // 解析SpEL表达式
        String value = expression.getValue(context, String.class);
        return redisLock.prefix() + value;
    }
} 