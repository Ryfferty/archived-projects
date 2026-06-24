package com.xspaceagi.system.spec.constants;

/**
 * Redis Key 常量统一管理
 * <p>
 * 系统为租户隔离架构，所有 Redis 缓存 key 均需动态加上 tenantId 实现租户隔离。
 * 格式示例：{prefix}:{tenantId}:{businessId}
 * </p>
 */
public class RedisKeyConstants {

    private RedisKeyConstants() {
        // 工具类，禁止实例化
    }

    // ==================== 权限相关缓存 ====================

    /**
     * 用户权限缓存 key 前缀（使用Hash存储）
     * 完整 key 格式：user:permission:{tenantId}:{userId}
     */
    public static final String USER_PERMISSION_CACHE_KEY = "user:permission:";

    /**
     * 权限最新生效时间 key 前缀（按租户隔离，用于判断缓存是否失效）
     * 完整 key 格式：permission:latest:time:{tenantId}
     */
    public static final String PERMISSION_LATEST_TIME_KEY = "permission:latest:time:";

    /**
     * 构建用户权限缓存 key（租户隔离）
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return user:permission:{tenantId}:{userId}
     */
    public static String buildUserPermissionCacheKey(Long tenantId, Long userId) {
        return USER_PERMISSION_CACHE_KEY + tenantId + ":" + userId;
    }

    /**
     * 构建权限最新生效时间 key（租户隔离）
     *
     * @param tenantId 租户ID
     * @return permission:latest:time:{tenantId}
     */
    public static String buildPermissionLatestTimeKey(Long tenantId) {
        return PERMISSION_LATEST_TIME_KEY + tenantId;
    }

    /**
     * Hash存储字段名：菜单树
     */
    public static final String HASH_FIELD_MENU_TREE = "menuTree";

    /**
     * Hash存储字段名：资源码集合
     */
    public static final String HASH_FIELD_RESOURCE_CODES = "resourceCodes";

    /**
     * Hash存储字段名：数据权限
     */
    public static final String HASH_FIELD_DATA_PERMISSION = "dataPermission";

    /**
     * Hash存储字段名：用户角色ID集合
     */
    public static final String HASH_FIELD_ROLE_IDS = "roleIds";

    /**
     * Hash存储字段名：用户组ID集合
     */
    public static final String HASH_FIELD_GROUP_IDS = "groupIds";

    /**
     * Hash存储字段名：缓存生成时间（毫秒时间戳）
     */
    public static final String HASH_FIELD_CACHE_TIME = "cacheTime";

    /**
     * 用户权限相关缓存过期时间（秒）
     */
    public static final long USER_PERMISSION_CACHE_EXPIRE_SECONDS = 24 * 60 * 60L; // 24小时

    /**
     * 按用户清除缓存的阈值：超过此数量的用户时，改为清除全部缓存（避免大量定向删除占用资源）
     */
    public static final int CLEAR_CACHE_BY_USER_IDS_THRESHOLD = 500;
}

