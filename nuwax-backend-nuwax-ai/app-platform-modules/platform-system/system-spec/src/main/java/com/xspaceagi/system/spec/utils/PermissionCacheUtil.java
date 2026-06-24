package com.xspaceagi.system.spec.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.xspaceagi.system.spec.constants.RedisKeyConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 权限缓存工具类
 */
@Slf4j
public class PermissionCacheUtil {

    private PermissionCacheUtil() {
        // 工具类，禁止实例化
    }

    /**
     * 检查缓存是否有效（租户隔离）
     * 通过比较缓存生成时间和该租户的权限最新生效时间来判断
     *
     * @param stringRedisTemplate Redis 模板
     * @param tenantId            租户ID（为 null 时认为缓存失效）
     * @param cacheTime           缓存生成时间
     * @return 缓存是否有效
     */
    public static boolean isCacheValid(StringRedisTemplate stringRedisTemplate, Long tenantId, long cacheTime) {
        if (tenantId == null) {
            log.debug("tenantId 为空，认为缓存失效");
            return false;
        }
        try {
            String latestTimeKey = RedisKeyConstants.buildPermissionLatestTimeKey(tenantId);
            String latestTimeStr = stringRedisTemplate.opsForValue().get(latestTimeKey);
            if (latestTimeStr == null) {
                // 如果key不存在，可能是过期或被删除了，重新设置当前时间并认为缓存失效
                long currentTime = System.currentTimeMillis();
                stringRedisTemplate.opsForValue().set(latestTimeKey, String.valueOf(currentTime));
                log.debug("权限最新时间key不存在，已重新设置, tenantId={}, time={}", tenantId, currentTime);
                return false;
            }
            long latestTime = Long.parseLong(latestTimeStr);
            // 如果缓存时间在最新时间之后，则缓存有效
            return cacheTime > latestTime;
        } catch (Exception e) {
            log.warn("检查缓存有效性失败, tenantId={}, cacheTime={}", tenantId, cacheTime, e);
            // 异常情况下，为了安全起见，认为缓存失效
            return false;
        }
    }
}

