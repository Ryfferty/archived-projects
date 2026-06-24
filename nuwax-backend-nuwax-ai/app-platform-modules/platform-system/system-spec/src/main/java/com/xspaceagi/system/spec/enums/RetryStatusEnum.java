package com.xspaceagi.system.spec.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态，1待重试，2重试成功，3重试失败，4禁止重试
 */
@Getter
@AllArgsConstructor
public enum RetryStatusEnum implements IEnum<Integer> {

    WAIT(1, "待重试"),
    SUCCESS(2, "重试成功"),
    FAIL(3, "重试失败"),
    BAN(4, "禁止重试"),
    ;

    private final Integer value;

    private final String desc;

    public static boolean canRetry(RetryStatusEnum status) {
        return WAIT.equals(status);
    }

}

