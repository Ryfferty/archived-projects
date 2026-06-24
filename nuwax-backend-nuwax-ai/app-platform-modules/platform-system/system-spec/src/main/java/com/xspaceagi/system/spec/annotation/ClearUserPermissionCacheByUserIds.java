package com.xspaceagi.system.spec.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 执行后按用户ID列表清理权限缓存
 *
 * 通过 userIdParamIndexes 指定方法参数中哪些位置包含用户ID或用户ID集合：
 * - 如果参数类型是 Long，则视为单个 userId
 * - 如果是 Collection<Long> / List<Long>，则视为用户ID列表
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearUserPermissionCacheByUserIds {

    /**
     * 方法参数中，包含用户ID或用户ID集合的参数下标（从 0 开始）
     */
    int[] userIdParamIndexes();
}

