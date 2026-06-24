package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowExecutingDto implements Serializable {

    @Schema(description = "执行结果状态")
    private boolean success;

    @Schema(description = "执行结果")
    private Object data;

    @Schema(description = "执行结果码")
    private String code;

    @Schema(description = "执行错误信息")
    private String message;

    @Schema(description = "请求ID")
    private String requestId;

    // 各个节点执行结果信息
    private Object nodeExecuteResultMap;

    @Schema(description = "请求耗时")
    private Long costTime;

    @Schema(description = "工作流是否执行完成")
    private boolean isComplete;

    public String getCode() {
        return success ? "0000" : "0001";
    }
}
