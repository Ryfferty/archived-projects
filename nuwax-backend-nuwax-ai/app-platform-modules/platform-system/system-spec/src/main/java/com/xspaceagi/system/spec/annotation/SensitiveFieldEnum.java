package com.xspaceagi.system.spec.annotation;

import lombok.Getter;

@Getter
public enum SensitiveFieldEnum {

    /**
     *
     */
    USER_PASSWORD("用户密码");

    /**
     * 描述
     */
    private String desc;

    SensitiveFieldEnum(String desc) {
        this.desc = desc;

    }
}
