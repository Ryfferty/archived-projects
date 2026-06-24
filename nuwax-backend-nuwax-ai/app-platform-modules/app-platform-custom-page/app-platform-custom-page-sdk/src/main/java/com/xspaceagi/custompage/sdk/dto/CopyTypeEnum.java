package com.xspaceagi.custompage.sdk.dto;

public enum CopyTypeEnum {
    DEVELOP, // 开发
    SQUARE; // 广场

    public static CopyTypeEnum getByValue(String name) {
        if (name == null) {
            return null;
        }
        for (CopyTypeEnum type : CopyTypeEnum.values()) {
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