package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 系统内置用户组
 */
@Getter
public enum GroupEnum {

    DEFAULT_GROUP("default_group", "默认用户组");


    private final String code;
    private final String name;

    GroupEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GroupEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (GroupEnum resourceEnum : values()) {
            if (resourceEnum.getCode().equals(code)) {
                return resourceEnum;
            }
        }
        return null;
    }
}
