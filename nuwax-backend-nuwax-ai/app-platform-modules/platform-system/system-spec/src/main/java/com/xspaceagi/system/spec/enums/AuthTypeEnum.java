package com.xspaceagi.system.spec.enums;

public enum AuthTypeEnum {

    PHONE(1, "手机"),
    EMAIL(3, "邮箱"),
    CAS(2, "CAS");

    private int code;

    private String description;

    AuthTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
