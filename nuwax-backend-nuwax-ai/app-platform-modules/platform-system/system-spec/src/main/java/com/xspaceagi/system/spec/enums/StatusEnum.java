package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 状态枚举
 */
@Getter
public enum StatusEnum {
    
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");
    
    private final Integer code;
    private final String desc;
    
    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static StatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (StatusEnum statusEnum : values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static boolean isEnabled(Integer code) {
        return ENABLED.getCode().equals(code);
    }

    public static boolean isValid(Integer code) {
        return getByCode(code) != null;
    }

    public static boolean isInValid(Integer code) {
        return !isValid(code);
    }
}

