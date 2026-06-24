package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class TableDataDeleteNodeConfigDto extends TableNodeConfigDto {


    @Schema(description = "条件参数之间关系")
    private ConditionTypeEnum conditionType;

    //参数列表配置
    @Schema(description = "参数列表配置")
    private List<ConditionArgDto> conditionArgs;
}
