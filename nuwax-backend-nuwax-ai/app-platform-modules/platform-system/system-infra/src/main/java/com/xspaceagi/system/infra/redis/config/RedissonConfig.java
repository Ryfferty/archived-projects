package com.xspaceagi.system.infra.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

/**
 * Redisson配置类
 * 用于配置Redis分布式锁客户端
 */
@Configuration
public class RedissonConfig {
    
    /** Redis连接超时时间，默认10秒 */
    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    /** Redis命令执行超时时间，默认3秒 */
    private static final int DEFAULT_TIMEOUT = 3000;
    
    /**
     * 创建RedissonClient实例
     *
     * @param redisProperties Redis配置属性
     * @return RedissonClient实例
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        String address = String.format("redis://%s:%s", redisProperties.getHost(), redisProperties.getPort());
        
        config.useSingleServer()
            .setAddress(address)
            .setDatabase(redisProperties.getDatabase())
            .setPassword(redisProperties.getPassword())
            .setConnectTimeout(getTimeout(redisProperties.getConnectTimeout(), DEFAULT_CONNECT_TIMEOUT))
            .setTimeout(getTimeout(redisProperties.getTimeout(), DEFAULT_TIMEOUT));
            
        return Redisson.create(config);
    }
    
    /**
     * 获取超时时间，如果配置的超时时间为空则使用默认值
     *
     * @param duration 配置的超时时间
     * @param defaultValue 默认超时时间（毫秒）
     * @return 最终的超时时间（毫秒）
     */
    private int getTimeout(Duration duration, int defaultValue) {
        return duration != null ? (int)duration.toMillis() : defaultValue;
    }
} 