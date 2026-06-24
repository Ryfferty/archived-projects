package com.xspaceagi.knowledge.core.spec.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * CAFFEINE 缓存工具类
 */
public class CaffeineCacheUtil {

    /**
     * 默认缓存最大容量
     */
    private static final int DEFAULT_MAXIMUM_SIZE = 10000;

    /**
     * 默认过期时间（分钟）
     */
    private static final int DEFAULT_EXPIRE_MINUTES = 1;

    /**
     * 文件大小缓存 - 针对特定知识库的总文件大小查询
     * 缓存键: 知识库ID
     * 缓存值: 文件总大小
     */
    private static final Cache<Long, Long> FILE_SIZE_CACHE = Caffeine.newBuilder()
            .maximumSize(DEFAULT_MAXIMUM_SIZE)
            .expireAfterWrite(DEFAULT_EXPIRE_MINUTES, TimeUnit.MINUTES)
            .build();


    /**
     * 租户客户端ID缓存 - 针对特定租户的客户端ID查询
     * 缓存键: 租户ID
     * 缓存值: 客户端ID
     */
    private static final Cache<Long, String> TENANT_CLIENT_ID_CACHE = Caffeine.newBuilder()
            .maximumSize(DEFAULT_MAXIMUM_SIZE)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();


    /**
     * 从租户客户端ID缓存中获取值，如果不存在则计算并缓存
     * @param tenantId 租户ID
     * @param dbFunction 从数据库查询的函数
     * @return 客户端ID
     */
    public static String getTenantClientIdWithCache(Long tenantId) {
        if (Objects.isNull(tenantId)) {
            return null;
        }

        return TENANT_CLIENT_ID_CACHE.get(tenantId, key -> {
            String clientId = UUID.randomUUID().toString().replace("-", "");
            TENANT_CLIENT_ID_CACHE.put(tenantId, clientId);
            return clientId;
        });
    }

    /**
     * 从文件大小缓存中获取值，如果不存在则计算并缓存
     * @param kbId 知识库ID
     * @param dbFunction 从数据库查询的函数
     * @return 文件总大小
     */
    public static Long getFileSizeWithCache(Long kbId, Function<Long, Long> dbFunction) {
        if (Objects.isNull(kbId)) {
            return 0L;
        }

        return FILE_SIZE_CACHE.get(kbId, key -> {
            Long result = dbFunction.apply(key);
            return Objects.isNull(result) ? 0L : result;
        });
    }

    /**
     * 清除特定知识库ID的文件大小缓存
     * @param kbId 知识库ID
     */
    public static void invalidateFileSizeCache(Long kbId) {
        if (Objects.nonNull(kbId)) {
            FILE_SIZE_CACHE.invalidate(kbId);
        }
    }

    /**
     * 清除所有文件大小缓存
     */
    public static void invalidateAllFileSizeCache() {
        FILE_SIZE_CACHE.invalidateAll();
    }

    /**
     * 创建自定义缓存
     * @param maximumSize 最大缓存数量
     * @param expireMinutes 过期时间（分钟）
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 新的缓存实例
     */
    public static <K, V> Cache<K, V> createCache(int maximumSize, int expireMinutes) {
        return Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterWrite(expireMinutes, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 清除特定知识库ID的缓存并重新计算
     * @param kbId 知识库ID
     * @param dbFunction 从数据库查询的函数
     * @return 重新计算后的文件总大小
     */
    public static Long refreshFileSizeCache(Long kbId, Function<Long, Long> dbFunction) {
        if (Objects.isNull(kbId)) {
            return 0L;
        }
        
        // 清除旧缓存
        FILE_SIZE_CACHE.invalidate(kbId);
        
        // 重新计算并缓存
        Long result = dbFunction.apply(kbId);
        Long value = Objects.isNull(result) ? 0L : result;
        FILE_SIZE_CACHE.put(kbId, value);
        
        return value;
    }
}