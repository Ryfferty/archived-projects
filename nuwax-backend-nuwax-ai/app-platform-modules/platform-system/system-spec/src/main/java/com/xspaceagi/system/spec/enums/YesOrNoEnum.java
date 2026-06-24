package com.xspaceagi.system.spec.enums;

import lombok.Getter;

public enum YesOrNoEnum {
    N(0, "无效"),
    Y(1, "有效");
    @Getter
    private final Integer key;
    private final String value;

    YesOrNoEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static boolean isValid(Integer key) {
        for (YesOrNoEnum value : YesOrNoEnum.values()) {
            if (value.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInValid(Integer key) {
        return !isValid(key);
    }
}
