package com.xspaceagi.system.spec.constants;

import java.util.List;
import java.util.Set;

import static com.xspaceagi.system.spec.enums.MenuEnum.ROLE_MANAGE;
import static com.xspaceagi.system.spec.enums.ResourceEnum.ROLE_MANAGE_BIND_MENU;
import static com.xspaceagi.system.spec.enums.ResourceEnum.ROLE_MANAGE_QUERY;

public class PermissionConstants {
    private PermissionConstants() {
    }

    // 超管必须具备的菜单
    public static Set<String> superAdminNecessaryMenus = Set.of(
            ROLE_MANAGE.getCode()
    );

    // 超管必须具备的资源
    public static Set<String> superAdminNecessaryResources = Set.of(
            ROLE_MANAGE_QUERY.getCode(),
            ROLE_MANAGE_BIND_MENU.getCode()
    );
}
