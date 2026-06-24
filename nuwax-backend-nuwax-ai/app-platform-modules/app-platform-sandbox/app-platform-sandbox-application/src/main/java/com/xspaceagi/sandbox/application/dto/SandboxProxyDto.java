package com.xspaceagi.sandbox.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 临时代理 DTO
 */
@Schema(description = "临时代理信息")
@Data
public class SandboxProxyDto {

    @Schema(description = "代理ID")
    private Long id;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "沙盒ID")
    private Long sandboxId;

    @Schema(description = "代理键")
    private String proxyKey;

    @Schema(description = "后端主机地址")
    private String backendHost;

    @Schema(description = "后端端口")
    private Integer backendPort;

    @Schema(description = "创建时间")
    private Date created;

    @Schema(description = "更新时间")
    private Date modified;
}
