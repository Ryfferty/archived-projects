package com.xspaceagi.system.spec.enums;

import lombok.Getter;

/**
 * 时段类型枚举
 */
@Getter
public enum PeriodTypeEnum {

    YEAR("YEAR", "年"),
    MONTH("MONTH", "月"),
    DAY("DAY", "天"),
    HOUR("HOUR", "时");

    private final String code;
    private final String desc;

    PeriodTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PeriodTypeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (PeriodTypeEnum type : PeriodTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static boolean isValid(String code) {
        return fromCode(code) != null;
    }

    public static boolean isInValid(String code) {
        return !isValid(code);
    }
}