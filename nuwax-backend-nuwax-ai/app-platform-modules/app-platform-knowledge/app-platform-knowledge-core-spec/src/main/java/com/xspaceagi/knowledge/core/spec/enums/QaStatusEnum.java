package com.xspaceagi.knowledge.core.spec.enums;

import lombok.Getter;
import java.util.Arrays;

/**
 * 问答状态,-1:待生成问答;1:已生成问答;
 */
@Getter
public enum QaStatusEnum {
    /**
     * 待生成问答
     */
    PENDING(-1),
    /**
     * 已生成问答
     */
    GENERATED(1);

    private final Integer code;

    QaStatusEnum(Integer code) {
        this.code = code;
    }

    public static QaStatusEnum getEnumByCode(Integer code) {
        return Arrays.stream(QaStatusEnum.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElse(null);
    }
}
