package com.xspaceagi.knowledge.core.spec.enums;

import lombok.Getter;

/**
 * 文档分段rawText的全文检索同步状态
 */
@Getter
public enum RawTextFulltextSyncStatusEnum {
    UNSYNCED(0, "未同步"),
    SYNCED(1, "已同步");

    private final int code;
    private final String desc;

    RawTextFulltextSyncStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RawTextFulltextSyncStatusEnum getByCode(int code) {
        for (RawTextFulltextSyncStatusEnum value : RawTextFulltextSyncStatusEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
