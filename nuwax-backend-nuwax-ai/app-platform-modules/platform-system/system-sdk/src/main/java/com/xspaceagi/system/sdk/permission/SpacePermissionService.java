package com.xspaceagi.system.sdk.permission;

import java.util.List;

public interface SpacePermissionService {

    void checkSpaceAdminPermission(Long id);

    void checkSpaceOwnerPermission(Long id);

    void checkSpaceUserPermission(Long id);

    void checkSpaceUserPermission(Long id, Long userId);


    /**
     * 根据用户id,查询用户有权限的空间id集合
     *
     * @param userId 用户id
     * @return 空间id集合
     */
    List<Long> querySpaceIdList(Long userId);
}
