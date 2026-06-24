package com.xspaceagi.knowledge.man.ui.web.dto.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfoVo {

    @Schema(description = "文档名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "文档URL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String docUrl;

    @Schema(description = "文件大小,单位是字节Byte", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fileSize;
}
