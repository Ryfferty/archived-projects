package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 系统内置菜单（部分）特殊用到的定义到这里
 */
@Getter
public enum MenuEnum {
    ROOT("root", "根节点", "根节点"),

    ECO_MARKET("eco_market", "生态市场", "root"),
    SYSTEM_MANAGE("system_manage", "系统管理", "root"),


    PERMISSION_MANAGE("permission_manage", "权限管理", "system_manage"),
    RESOURCE_MANAGE("resource_manage", "资源管理", "permission_manage"),
    MENU_MANAGE("menu_manage", "菜单管理", "permission_manage"),
    ROLE_MANAGE("role_manage", "角色管理", "permission_manage"),
    USER_GROUP_MANAGE("user_group_manage", "用户组管理", "permission_manage");

    private final String code;
    private final String name;
    private final String parentCode;

    MenuEnum(String code, String name, String parentCode) {
        this.code = code;
        this.name = name;
        this.parentCode = parentCode;
    }

    /**
     * 根据code获取枚举
     */
    public static MenuEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (MenuEnum resourceEnum : values()) {
            if (resourceEnum.getCode().equals(code)) {
                return resourceEnum;
            }
        }
        return null;
    }
}
