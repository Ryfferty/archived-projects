package com.xspaceagi.custompage.sdk.dto;

/**
 * 来源类型
 */
public enum SourceTypeEnum {
    SYSTEM, // 系统
    USER; // 用户

    public static SourceTypeEnum getByValue(String name) {
        if (name == null) {
            return null;
        }
        for (SourceTypeEnum type : SourceTypeEnum.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static boolean isValid(String name) {
        return getByValue(name) != null;
    }
}