package com.xspaceagi.sandbox.ui.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SandboxTempLinkDto {

    @Schema(description = "外部可访问的临时代理链接地址")
    private String tempLink;
    @Schema(description = "代理链接ID，用于管理该链接，比如删除时使用")
    private Long id;

}
