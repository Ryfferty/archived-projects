package com.xspaceagi.system.application.dto;

import com.xspaceagi.system.spec.enums.TenantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TenantDto implements Serializable {

    @Schema(description = "租户ID")
    private Long id;

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户描述")
    private String description;

    @Schema(description = "租户状态")
    private TenantStatus status;

    @Schema(description = "租户域名")
    private String domain;

    @Schema(description = "创建时间")
    private Date created;

    @Schema(description = "配置信息")
    private List<TenantConfigItemDto> tenantConfigs;
}
