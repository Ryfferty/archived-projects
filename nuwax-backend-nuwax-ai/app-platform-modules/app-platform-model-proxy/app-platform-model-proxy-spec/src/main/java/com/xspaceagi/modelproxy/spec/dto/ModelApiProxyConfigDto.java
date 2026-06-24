package com.xspaceagi.modelproxy.spec.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 模型API代理配置DTO
 */
@Schema(description = "模型API代理配置")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelApiProxyConfigDto implements Serializable {

    @Schema(description = "配置ID")
    private Long id;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "用户API Key")
    private String userApiKey;

    @Schema(description = "后端API URL")
    private String baseUrl;

    @Schema(description = "后端API Key")
    private String backendApiKey;

    @Schema(description = "模型标识")
    private String model;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间")
    private Date created;

    @Schema(description = "修改时间")
    private Date modified;
}
