package com.xspaceagi.sandbox.spec.enums;

import lombok.Getter;

/**
 * 沙盒配置类型枚举
 */
@Getter
public enum SandboxConfigTypeEnum {
    RESOURCE(0, "resource", "资源配置"),
    SERVERS(1, "servers", "服务器配置"),
    POLICY(2, "policy", "策略配置");

    private final Integer key;
    private final String code;
    private final String description;

    SandboxConfigTypeEnum(Integer key, String code, String description) {
        this.key = key;
        this.code = code;
        this.description = description;
    }

    public static SandboxConfigTypeEnum fromCode(String code) {
        for (SandboxConfigTypeEnum value : SandboxConfigTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static SandboxConfigTypeEnum fromKey(Integer key) {
        for (SandboxConfigTypeEnum value : SandboxConfigTypeEnum.values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }
        return null;
    }
}
