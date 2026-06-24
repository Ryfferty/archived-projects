package com.xspaceagi.system.application.service;

import java.util.List;

import com.xspaceagi.system.application.dto.permission.MenuNodeDto;

/**
 * 用户权限缓存管理
 */
public interface SysUserPermissionCacheService {

    /**
     * 清除当前租户下指定用户的权限缓存
     */
    void clearCacheByUserIds(List<Long> userIds);

    /**
     * 清除指定租户下指定用户的权限缓存
     */
    void clearCacheByTenantAndUserIds(Long tenantId, List<Long> userIds);

    /**
     * 清除当前租户所有用户的权限缓存
     */
    void clearCacheAll();

    /**
     * 清除指定租户下所有用户的权限缓存
     */
    void clearCacheAllByTenant(Long tenantId);

    /**
     * 获取用户的菜单权限树（优先用缓存）
     */
    List<MenuNodeDto> getUserMenuTree(Long userId);

    /**
     * 检查用户是否拥有任一指定资源的权限（OR 逻辑）
     * 若用户拥有 resourceCodes 中任意一个资源的权限则通过，否则抛出异常
     * 优先使用缓存的用户资源码进行验证
     */
    void checkResourcePermissionAny(Long userId, List<String> resourceCodes);

    /**
     * 获取当前租户的全局权限缓存时间（权限最新生效时间，毫秒时间戳）
     */
    Long getPermissionLatestCacheTime();

    /**
     * 清理角色下用户的权限缓存。先 count 判断数量，超过阈值则全量失效，否则仅清理指定用户缓存
     */
    void clearCacheForRoleUsers(Long roleId);

    /**
     * 清理用户组下用户的权限缓存。先 count 判断数量，超过阈值则全量失效，否则仅清理指定用户缓存
     */
    void clearCacheForGroupUsers(Long groupId);

}