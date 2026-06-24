package com.xspaceagi.system.sdk.service.dto;

import lombok.Getter;

/**
 * 时段类型常量（供 RPC 调用使用）
 */
@Getter
public class PeriodType {

    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String DAY = "DAY";
    public static final String HOUR = "HOUR";

    private PeriodType() {
    }
}
