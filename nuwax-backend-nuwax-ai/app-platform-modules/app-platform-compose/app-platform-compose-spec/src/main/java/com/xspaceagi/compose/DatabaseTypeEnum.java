package com.xspaceagi.compose;

import lombok.Getter;

/**
 * mysql,doris 数据库类型
 */
@Getter
public enum DatabaseTypeEnum {
    MYSQL("mysql"),
    DORIS("doris");

    private String type;

    DatabaseTypeEnum(String type) {
        this.type = type;
    }
}
