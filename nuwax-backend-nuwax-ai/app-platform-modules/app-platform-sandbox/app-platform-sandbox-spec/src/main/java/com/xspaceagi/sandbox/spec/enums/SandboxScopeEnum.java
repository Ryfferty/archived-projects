package com.xspaceagi.sandbox.spec.enums;

import lombok.Getter;

/**
 * 沙盒配置范围枚举
 */
@Getter
public enum SandboxScopeEnum {
    GLOBAL(0, "global", "全局配置"),
    USER(1, "user", "个人配置");

    private final Integer key;
    private final String code;
    private final String description;

    SandboxScopeEnum(Integer key, String code, String description) {
        this.key = key;
        this.code = code;
        this.description = description;
    }

    public static SandboxScopeEnum fromCode(String code) {
        for (SandboxScopeEnum value : SandboxScopeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static SandboxScopeEnum fromKey(Integer key) {
        for (SandboxScopeEnum value : SandboxScopeEnum.values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }
        return null;
    }
}
