package com.xspaceagi.knowledge.core.spec.enums;

import lombok.Getter;

/**
 * 全文检索同步状态
 */
@Getter
public enum FulltextSyncStatusEnum {
    UNSYNCED(0, "未同步"),
    SYNCHRONIZING(1, "同步中"),
    SYNCED(2, "已同步"),
    SYNC_FAILED(-1, "同步失败");

    private final int code;
    private final String desc;

    FulltextSyncStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FulltextSyncStatusEnum getByCode(int code) {
        for (FulltextSyncStatusEnum value : FulltextSyncStatusEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
