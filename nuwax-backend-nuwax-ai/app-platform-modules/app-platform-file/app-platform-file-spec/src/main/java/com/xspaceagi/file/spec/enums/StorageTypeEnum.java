package com.xspaceagi.file.spec.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存储类型枚举
 */
@Getter
@AllArgsConstructor
public enum StorageTypeEnum {

    /**
     * 腾讯云COS
     */
    COS("cos", "腾讯云COS"),

    /**
     * 阿里云OSS
     */
    OSS("oss", "阿里云OSS"),

    /**
     * S3协议
     */
    S3("s3", "S3协议"),

    /**
     * 本地存储
     */
    FILE("file", "本地存储");

    private final String code;
    private final String desc;

    public static StorageTypeEnum fromCode(String code) {
        for (StorageTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return FILE;
    }
}
