package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ExceptionHandleConfigDto {

    @Schema(description = "重试次数，0为不重试，最多可重试3次")
    private Integer retryCount;

    @Schema(description = "超时时间，秒，默认180秒")
    private Integer timeout;

    @Schema(description = "异常处理类型；INTERRUPT 中断流程； SPECIFIC_CONTENT 返回特定内容；EXECUTE_EXCEPTION_FLOW 执行异常流程")
    private ExceptionHandleTypeEnum exceptionHandleType;

    @Schema(description = "异常处理类型为特定内容时，需要填写")
    private Object specificContent;

    @Schema(description = "异常处理节点ID列表")
    private List<Long> exceptionHandleNodeIds;

    public enum ExceptionHandleTypeEnum {
        //中断流程
        INTERRUPT,
        //返回特定内容
        SPECIFIC_CONTENT,
        //执行异常流程
        EXECUTE_EXCEPTION_FLOW,
    }
}
