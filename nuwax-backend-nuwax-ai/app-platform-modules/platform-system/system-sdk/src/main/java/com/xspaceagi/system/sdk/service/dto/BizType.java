package com.xspaceagi.system.sdk.service.dto;

import lombok.Getter;

/**
 * 业务类型枚举
 */
@Getter
public enum BizType {

    TOKEN_USAGE("TOKEN_USAGE", "Token用量"),
    GENERAL_AGENT_CHAT("GENERAL_AGENT_CHAT", "通用智能体对话次数"),
    APP_DEV_CHAT("APP_DEV_CHAT", "应用开发对话次数");

    private final String code;
    private final String description;

    BizType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static BizType fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (BizType bizType : values()) {
            if (bizType.getCode().equals(code)) {
                return bizType;
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
