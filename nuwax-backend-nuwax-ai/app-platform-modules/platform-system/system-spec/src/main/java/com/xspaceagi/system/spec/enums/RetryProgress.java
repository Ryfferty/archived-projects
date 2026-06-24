package com.xspaceagi.system.spec.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 重试进度
 */
@Getter
@AllArgsConstructor
public enum RetryProgress {

    INIT(1, "初次上报"),
    OVERFLOW(2, "重试超过最大次数"),
    SUCCESS(3, "成功"),
    ;

    private Integer value;
    private String  desc;

}
