package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PluginExecuteResultDto implements Serializable {

    @Schema(description = "执行结果状态")
    private boolean success;

    @Schema(description = "执行结果")
    private Object result;

    @Schema(description = "执行日志")
    private List<String> logs;

    @Schema(description = "执行错误信息")
    private String error;

    @Schema(description = "请求ID")
    private String requestId;

    @Schema(description = "请求耗时")
    private Long costTime;
}
