package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 来源枚举
 */
@Getter
public enum PermissionTargetTypeEnum {

    USER(1, "用户"),
    ROLE(2, "角色"),
    GROUP(3, "用户组");

    private final Integer code;
    private final String desc;

    PermissionTargetTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PermissionTargetTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (PermissionTargetTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static boolean isValid(Integer code) {
        return getByCode(code) != null;
    }

    public static boolean isInValid(Integer code) {
        return !isValid(code);
    }
}

