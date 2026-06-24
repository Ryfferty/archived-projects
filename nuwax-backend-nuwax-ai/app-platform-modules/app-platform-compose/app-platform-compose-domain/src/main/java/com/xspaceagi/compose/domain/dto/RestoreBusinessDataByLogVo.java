package com.xspaceagi.compose.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 根据操作日志恢复业务数据的请求
 */
@Schema(description = "根据操作日志恢复业务数据的请求")
@Getter
@Setter
public class RestoreBusinessDataByLogVo {

    /**
     * 操作日志的 extraContent JSON 字符串
     */
    @Schema(description = "操作日志的 extraContent JSON 字符串", required = true,
            example = "{\"httpParams\":{},\"spelData\":{\"tableId\":123,\"rowId\":456},\"spelExpression\":\"#request\"}")
    private String extraContent;

    /**
     * 是否强制恢复（如果数据已存在是否覆盖）
     */
    @Schema(description = "是否强制恢复（如果数据已存在是否覆盖，默认 false）")
    private Boolean forceRestore = false;
}
