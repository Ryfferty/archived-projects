package com.xspaceagi.compose.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 删除业务数据的请求,单行数据
 */
@Schema(description = "删除业务数据的请求,单行数据")
@Getter
@Setter
public class CustomDeleteBusinessRowDataVo {

    /**
     * 表ID
     */
    @Schema(description = "表ID")
    private Long tableId;


    /**
     * 行ID
     */
    @Schema(description = "行ID")
    private Long rowId;
 
}
