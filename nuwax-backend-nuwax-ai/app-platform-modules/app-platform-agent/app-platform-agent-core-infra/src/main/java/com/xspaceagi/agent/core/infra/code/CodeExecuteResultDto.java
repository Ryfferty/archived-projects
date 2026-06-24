package com.xspaceagi.agent.core.infra.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CodeExecuteResultDto implements Serializable {

    @Schema(description = "执行结果状态")
    private Boolean success;

    @Schema(description = "执行结果")
    private Object result;

    @Schema(description = "执行日志")
    private List<String> logs;

    @Schema(description = "执行错误信息")
    private String error;
}
