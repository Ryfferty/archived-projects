package com.xspaceagi.sandbox.infra.dao.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 沙盒配置值
 */
@Schema(description = "沙盒服务端内部交互信息")
@Data
public class SandboxServerInfo {

    @Schema(description = "协议")
    private String scheme;

    @Schema(description = "主机地址")
    private String host;

    @Schema(description = "Agent服务端口")
    private int agentPort;

    @Schema(description = "VNC服务端口")
    private int vncPort;

    @Schema(description = "文件服务端口")
    private int fileServerPort;

    @Schema(description = "API密钥")
    private String apiKey;
}
