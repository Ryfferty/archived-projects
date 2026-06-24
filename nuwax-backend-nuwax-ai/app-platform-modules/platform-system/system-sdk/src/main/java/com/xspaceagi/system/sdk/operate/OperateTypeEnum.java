package com.xspaceagi.system.sdk.operate;

import lombok.Getter;

/**
 * //1:操作类型;2:访问日志; 查询操作,默认都是访问日志
 */
@Getter
public enum OperateTypeEnum {

    ACCESS(2L, "访问日志"),
    OPERATE(1L, "操作日志");

    private Long type;

    private String desc;

    OperateTypeEnum(Long type, String desc) {
        this.type = type;
        this.desc = desc;
    }


}
