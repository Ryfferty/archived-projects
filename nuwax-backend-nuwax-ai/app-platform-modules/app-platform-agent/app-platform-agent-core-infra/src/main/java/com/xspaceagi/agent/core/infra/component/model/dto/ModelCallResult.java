package com.xspaceagi.agent.core.infra.component.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ModelCallResult implements Serializable {

    @Schema(description = "开始执行时间")
    private Long startTime;

    @Schema(description = "结束执行时间")
    private Long endTime;

    @Schema(description = "首字符回复时间")
    private Long firstResponseTime;

    @Schema(description = "输出token数")
    private Integer completionTokens;

    @Schema(description = "提示token数")
    private Integer promptTokens;

    @Schema(description = "总token数")
    private Integer totalTokens;

    @Schema(description = "输出文本")
    private String responseText;

    @Schema(description = "格式化后的数据")
    private Object data;
}
