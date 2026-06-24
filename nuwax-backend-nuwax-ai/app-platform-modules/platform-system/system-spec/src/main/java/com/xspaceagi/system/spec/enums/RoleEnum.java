package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 系统内置角色
 */
@Getter
public enum RoleEnum {

    SUPER_ADMIN("super_admin", "超级管理员");


    private final String code;
    private final String name;

    RoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RoleEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (RoleEnum resourceEnum : values()) {
            if (resourceEnum.getCode().equals(code)) {
                return resourceEnum;
            }
        }
        return null;
    }
}
