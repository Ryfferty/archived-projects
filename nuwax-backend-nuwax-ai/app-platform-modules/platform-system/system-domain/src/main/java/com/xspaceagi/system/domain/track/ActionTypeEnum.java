package com.xspaceagi.system.domain.track;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@AllArgsConstructor
@Getter
public enum ActionTypeEnum {

    EXPORT("导出"),
    PRINT("打印"),
    ADD("新增"),
    EFFECT("审核"),
    DELETE("删除"),
    MODIFY("修改"),
    COMMIT("提交"),
    HANDLE("受理"),
    CLOSE("关单"),
    ;

    private final String desc;
}
