package com.xspaceagi.system.spec.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 重试类型，1 异常数据需要重试 2 正常数据不需要重试
 */
@Getter
@AllArgsConstructor
public enum RetryTypeEnum implements IEnum<Integer> {

    Y(1, "异常数据需要重试"),
    N(2, "正常数据不需要重试"),
    ;

    private final Integer value;

    private final String desc;

}
