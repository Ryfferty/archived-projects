package com.xspaceagi.file.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 文件上传请求
 */
@Data
@Schema(description = "文件上传请求")
public class FileUploadRequest {

    @Schema(description = "租户ID", required = true)
    private Long tenantId;

    @Schema(description = "用户ID", required = true)
    private Long userId;

    @Schema(description = "来源对象类型")
    private String targetType;

    @Schema(description = "来源对象ID")
    private Long targetId;

    @Schema(description = "文件元数据（JSON格式）")
    private String metadata;
}
