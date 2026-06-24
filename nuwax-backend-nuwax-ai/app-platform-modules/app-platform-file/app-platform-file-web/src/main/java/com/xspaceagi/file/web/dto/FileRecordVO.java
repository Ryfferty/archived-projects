package com.xspaceagi.file.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文件记录VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件记录")
public class FileRecordVO {

    @Schema(description = "文件ID")
    private Long id;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "来源对象类型")
    private String targetType;

    @Schema(description = "来源对象ID")
    private Long targetId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件大小（字节）")
    private Long size;

    @Schema(description = "文件类型")
    private String mimeType;

    @Schema(description = "文件扩展名")
    private String fileExtension;

    @Schema(description = "文件元数据（JSON格式）")
    private String metadata;

    @Schema(description = "文件存储标识key")
    private String key;

    @Schema(description = "存储方式")
    private String storageType;

    @Schema(description = "文件访问URL")
    private String url;

    @Schema(description = "是否需要认证访问")
    private Boolean authRequired;

    @Schema(description = "文件状态")
    private String status;

    @Schema(description = "创建时间")
    private Date created;

    @Schema(description = "修改时间")
    private Date modified;
}
