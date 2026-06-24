package com.xspaceagi.compose.domain.dto;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 新增业务数据的请求,单行数据
 */
@Schema(description = "新增业务数据的请求,单行数据")
@Getter
@Setter
public class CustomAddBusinessRowDataVo {

    /**
     * 表ID
     */
    @Schema(description = "表ID")
    private Long tableId;

    /**
     * 行数据
     */
    @Schema(description = "行数据")
    private Map<String, Object> rowData;
}
