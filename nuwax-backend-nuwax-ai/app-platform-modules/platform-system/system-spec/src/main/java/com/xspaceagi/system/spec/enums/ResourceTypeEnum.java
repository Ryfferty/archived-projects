package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 资源类型枚举
 */
@Getter
public enum ResourceTypeEnum {

    MODULE(1, "模块"),
    OPERATION(2, "组件");

    private final Integer code;
    private final String desc;

    ResourceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResourceTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResourceTypeEnum typeEnum : values()) {
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

