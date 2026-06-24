package com.xspaceagi.system.spec.cache;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
@Slf4j
public class SimpleJvmHashCache {

    public static final long DEFAULT_EXPIRE_AFTER_SECONDS = 86400;

    private static final Map<String, Map<String, CacheItem<Object>>> cache;
    private static final ScheduledExecutorService cleanupExecutor;

    static {
        cache = new ConcurrentHashMap<>();
        cleanupExecutor = Executors.newSingleThreadScheduledExecutor();
        // 每5秒清理一次过期缓存
        cleanupExecutor.scheduleAtFixedRate(SimpleJvmHashCache::cleanupExpiredItems, 5, 5, TimeUnit.SECONDS);
    }

    /**
     * 添加Hash缓存项，指定过期时间（单位：秒）
     */
    public static void putHash(String key, String field, Object value, long expireAfterSeconds, Consumer<Object> expireListener) {
        if (key == null) {
            return;
        }
        long expireTime = System.currentTimeMillis() + expireAfterSeconds * 1000;
        cache.computeIfAbsent(key, k -> new ConcurrentHashMap<>())
                .put(field, new CacheItem<>(value, expireTime, expireListener));
    }

    public static void putHash(String key, String field, Object value, long expireAfterSeconds) {
        if (key == null) {
            return;
        }
        long expireTime = System.currentTimeMillis() + expireAfterSeconds * 1000;
        cache.computeIfAbsent(key, k -> new ConcurrentHashMap<>())
                .put(field, new CacheItem<>(value, expireTime));
    }

    /**
     * 获取Hash缓存项，如果已过期则返回null
     */
    public static Object getHash(String key, String field) {
        if (key == null) {
            return null;
        }
        Map<String, CacheItem<Object>> fieldMap = cache.get(key);
        if (fieldMap == null) return null;

        CacheItem<Object> item = fieldMap.get(field);
        if (item == null || item.isExpired()) {
            fieldMap.remove(field);
            return null;
        }
        return item.getValue();
    }

    //getHashAll
    public static Map<String, Object> getHashAll(String key) {
        if (key == null) {
            return null;
        }
        Map<String, CacheItem<Object>> fieldMap = cache.get(key);
        if (fieldMap == null) return null;

        Map<String, Object> result = new ConcurrentHashMap<>();
        fieldMap.forEach((field, item) -> {
            if (item != null && !item.isExpired() && item.getValue() != null) {
                result.put(field, item.getValue());
            }
        });
        return result;
    }

    /**
     * 删除Hash缓存项
     */
    public static Object removeHash(String key, String field) {
        if (key == null) {
            return null;
        }
        Map<String, CacheItem<Object>> fieldMap = cache.get(key);
        if (fieldMap != null) {
            CacheItem<Object> item = fieldMap.remove(field);
            if (item != null) {
                return item.getValue();
            }
        }
        return null;
    }

    //删除
    public static void removeHashAll(String key) {
        if (key == null) {
            return;
        }
        cache.remove(key);
    }

    private static void cleanupExpiredItems() {
        try {
            cleanupExpiredItems0();
        } catch (Exception e) {
            // ignore
            log.error("cleanupExpiredItems error", e);
        }
    }

    /**
     * 清理所有过期的缓存项
     */
    private static void cleanupExpiredItems0() {
        List<String> toRemove = new ArrayList<>();
        cache.forEach((key, fieldMap) -> {
            fieldMap.entrySet().removeIf(entry -> {
                if (entry.getValue().isExpired()) {
                    if (entry.getValue().expireListener != null) {
                        try {
                            entry.getValue().expireListener.accept(entry.getValue().getValue());
                        } catch (Exception e) {
                            //  ignore
                        }
                    }
                    return true;
                }
                return false;
            });
            if (fieldMap.isEmpty()) {
                toRemove.add(key);
            }
        });
        toRemove.forEach(cache::remove);
    }

    /**
     * 关闭缓存清理任务
     */
    @PreDestroy
    public void shutdown() {
        cleanupExecutor.shutdown();
    }

    /**
     * 缓存项包装类，记录过期时间
     */
    private static class CacheItem<V> {
        private final V value;
        private final long expireTime;

        private Consumer<V> expireListener;

        CacheItem(V value, long expireTime) {
            this.value = value;
            this.expireTime = expireTime;
        }

        CacheItem(V value, long expireTime, Consumer<V> expireListener) {
            this.value = value;
            this.expireTime = expireTime;
            this.expireListener = expireListener;
        }

        V getValue() {
            return value;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }
}
