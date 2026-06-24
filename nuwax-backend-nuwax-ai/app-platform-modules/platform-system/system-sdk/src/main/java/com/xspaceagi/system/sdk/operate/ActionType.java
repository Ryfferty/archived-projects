package com.xspaceagi.system.sdk.operate;

public enum ActionType {

    ADD("新增"),
    MODIFY("修改"),
    DELETE("删除"),
    QUERY("查询"),
    AUDIT("审批"),
    ENABLE("启用"),
    DISABLED("禁用"),
    ;

    ActionType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }


}
