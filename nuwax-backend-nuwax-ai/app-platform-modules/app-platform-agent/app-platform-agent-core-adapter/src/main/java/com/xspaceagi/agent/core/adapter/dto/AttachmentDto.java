package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttachmentDto implements Serializable {

    private String fileKey;

    @Schema(description = "文件URL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileUrl;

    private String fileName;

    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mimeType;
}