package com.xspaceagi.eco.market.spec.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * CAFFEINE 缓存工具类
 */
public class EcoMarketCaffeineCacheUtil {

    /**
     * 默认缓存最大容量
     */
    private static final int DEFAULT_MAXIMUM_SIZE = 10000;

    /**
     * 默认过期时间（小时）
     */
    private static final int DEFAULT_EXPIRE_HOURS = 1;

    /**
     * 租户客户端ID缓存 - 针对特定租户的客户端ID查询
     * 缓存键: 租户ID
     * 缓存值: 客户端ID
     */
    private static final Cache<Long, String> TENANT_CLIENT_ID_CACHE = Caffeine.newBuilder()
            .maximumSize(DEFAULT_MAXIMUM_SIZE)
            .expireAfterWrite(DEFAULT_EXPIRE_HOURS, TimeUnit.HOURS)
            .build();

    /**
     * 从租户客户端ID缓存中获取值，如果不存在则计算并缓存
     * 
     * @param tenantId   租户ID
     * @return 客户端ID
     */
    public static String getTenantClientIdWithCache(Long tenantId) {
        if (Objects.isNull(tenantId)) {
            return null;
        }

        return TENANT_CLIENT_ID_CACHE.get(tenantId, key -> {
            return UUID.randomUUID().toString().replace("-", "");
        });
    }

    /**
     * 将租户客户端ID缓存到内存中
     * 
     * @param tenantId   租户ID
     * @param clientId   客户端ID
     */
    public static void putTenantClientIdWithCache(Long tenantId, String clientId) {
        TENANT_CLIENT_ID_CACHE.put(tenantId, clientId);
    }

}