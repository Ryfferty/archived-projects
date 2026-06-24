package com.xspaceagi.agent.core.spec.utils;


import com.xspaceagi.system.spec.cache.SimpleJvmHashCache;

/**
 * 该类用于确保在复制过程中，相同的目标对象只被复制一次
 */
public class CopyRelationCacheUtil {
    private static final long DEFAULT_EXPIRE_SECONDS = 60;

    public static void put(Object type, Object targetSpaceId, Object oldTypeId, Object newId) {
        if (type == null || oldTypeId == null || newId == null || targetSpaceId == null) {
            return;
        }
        SimpleJvmHashCache.putHash(type + "_" + targetSpaceId, oldTypeId.toString(), newId, DEFAULT_EXPIRE_SECONDS);
    }

    public static Object get(Object type, Object targetSpaceId, Object oldTypeId) {
        if (type == null || oldTypeId == null || targetSpaceId == null) {
            return null;
        }
        return SimpleJvmHashCache.getHash(type + "_" + targetSpaceId, oldTypeId.toString());
    }
}
