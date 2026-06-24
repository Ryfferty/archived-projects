package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 打开方式
 */
@Getter
public enum OpenTypeEnum {

    CURRENT_TAB(1, "当前标签页打开"),
    NEW_TAB(2, "新标签页打开");

    private final Integer code;
    private final String desc;

    OpenTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static OpenTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (OpenTypeEnum typeEnum : values()) {
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
