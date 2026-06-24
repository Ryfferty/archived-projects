package com.xspaceagi.system.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "用户计量信息")
@Data
public class UserMetricDto {

    @Schema(description = "计量ID")
    private Long id;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "业务类型")
    private String bizType;

    @Schema(description = "时段类型")
    private String periodType;

    @Schema(description = "时段值")
    private String period;

    @Schema(description = "计量值")
    private BigDecimal value;

    @Schema(description = "修改时间")
    private Date modified;

    @Schema(description = "创建时间")
    private Date created;
}