package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 绑定类型枚举
 */
@Getter
public enum BindTypeEnum {

    NONE(0, "NONE"),// 未绑定
    ALL(1, "ALL"),// 全部绑定
    PART( 2, "PART");//部分绑定

    private final Integer code;
    private final String desc;

    BindTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static BindTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BindTypeEnum typeEnum : values()) {
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

