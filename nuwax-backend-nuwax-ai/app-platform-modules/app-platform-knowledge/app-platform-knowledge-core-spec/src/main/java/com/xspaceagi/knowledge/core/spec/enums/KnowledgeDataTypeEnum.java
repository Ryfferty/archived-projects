package com.xspaceagi.knowledge.core.spec.enums;

import lombok.Getter;

/**
 * 文件类型,1:URL访问文件;2:自定义文本内容
 */
@Getter

public enum KnowledgeDataTypeEnum {
    URL_FILE(1, "URL访问文件"),
    CUSTOM_TEXT(2, "自定义文本内容");

    private final Integer code;
    private final String desc;

    KnowledgeDataTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static KnowledgeDataTypeEnum getEnumByCode(Integer code) {
        for (KnowledgeDataTypeEnum item : KnowledgeDataTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
