package com.xspaceagi.file.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 文件记录实体
 * @TableName file_record
 */
@TableName(value = "file_record")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileRecord {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    @TableField(value = "_tenant_id")
    private Long tenantId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 来源对象类型（如：agent、knowledge、message等）
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
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型（MIME类型）
     */
    private String fileType;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 文件元数据（JSON格式，存储图片宽高、视频时长等）
     */
    private String metadata;

    /**
     * 文件存储标识key
     */
    private String fileKey;

    /**
     * 存储方式（cos:腾讯云COS, oss:阿里云OSS, s3:S3协议, file:本地存储）
     */
    private String storageType;

    /**
     * 文件访问URL
     */
    private String fileUrl;

    /**
     * 是否需要认证访问（0:公开, 1:需要认证）
     */
    private Boolean authRequired;

    /**
     * 文件状态（active:正常, deleted:已删除）
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
