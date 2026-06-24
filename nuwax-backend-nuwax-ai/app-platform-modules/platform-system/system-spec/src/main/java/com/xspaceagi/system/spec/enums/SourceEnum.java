package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 来源枚举
 */
@Getter
public enum SourceEnum {

    SYSTEM(1, "系统内置"),
    CUSTOM(2, "用户自定义");

    private final Integer code;
    private final String desc;

    SourceEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static SourceEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (SourceEnum typeEnum : values()) {
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
