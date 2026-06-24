package com.xspaceagi.system.web.dto;

import com.xspaceagi.system.spec.enums.PeriodTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "用户计量更新请求")
public class UserMetricUpdateDto {

    @Schema(description = "用户ID", required = true)
    private Long userId;

    @Schema(description = "业务类型", required = true)
    private String bizType;

    @Schema(description = "时段类型", required = true)
    private String periodType;

    @Schema(description = "时段值", required = true)
    private String period;

    @Schema(description = "计量值", required = true)
    private BigDecimal value;

    @Schema(description = "操作类型：add-增加值，set-设置值", required = true)
    private String operationType;
}
