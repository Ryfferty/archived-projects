package com.xspaceagi.knowledge.core.spec.enums;

import lombok.Getter;

/**
 * 状态，0:初始状态,1待重试，2重试成功，3重试失败，4禁止重试
 */
@Getter
public enum KnowledgeTaskStageStatusEnum {
    INIT(0, "初始状态"),
    WAIT_RETRY(1, "待重试"),
    RETRY_SUCCESS(2, "重试成功"),
    RETRY_FAIL(3, "重试失败"),
    FORBID_RETRY(4, "禁止重试");

    private final int status;
    private final String desc;


    KnowledgeTaskStageStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static KnowledgeTaskStageStatusEnum getByStatus(int status) {
        for (KnowledgeTaskStageStatusEnum value : KnowledgeTaskStageStatusEnum.values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return null;
    }

}
