package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 逻辑删除标记
 */

@Getter
public enum YnEnum {
    N(-1, "无效"), Y(1, "有效");

    private final Integer key;

    private final String value;

    YnEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static boolean isY(Integer key) {
        return Y.getKey().equals(key);
    }
}
