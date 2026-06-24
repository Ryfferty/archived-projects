package com.xspaceagi.system.spec.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 角色类型，1:管理员角色;2:普通角色
 */
@Getter
public enum UserRoleTypeEnum {


    Manager(1, "管理员角色"),

    Normal(2, "普通角色");


    private final int key;

    private final String value;

    UserRoleTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    public static UserRoleTypeEnum getUserRoleTypeEnum(Integer key) {
        if (Objects.isNull(key)) {
            return null;
        }

        for (UserRoleTypeEnum userRoleTypeEnum : UserRoleTypeEnum.values()) {
            if (userRoleTypeEnum.getKey() == key) {
                return userRoleTypeEnum;
            }
        }
        return null;
    }

}
