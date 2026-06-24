package com.xspaceagi.custompage.sdk.dto;

public enum PublishTypeEnum {
    AGENT, // 智能体
    PAGE; // 页面组件

    public static PublishTypeEnum getByValue(String name) {
        if (name == null) {
            return null;
        }
        for (PublishTypeEnum type : PublishTypeEnum.values()) {
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