package com.xspaceagi.file.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文件记录领域模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRecordDomain {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 来源对象类型
     */
    private String targetType;

    /**
     * 来源对象ID
     */
    private Long targetId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 文件元数据
     */
    private String metadata;

    /**
     * 文件key
     */
    private String fileKey;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 文件URL
     */
    private String fileUrl;

    /**
     * 是否需要认证访问
     */
    private Boolean authRequired;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;
}
