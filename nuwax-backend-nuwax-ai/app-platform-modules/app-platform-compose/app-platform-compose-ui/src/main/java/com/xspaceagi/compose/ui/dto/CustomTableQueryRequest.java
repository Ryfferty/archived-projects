package com.xspaceagi.compose.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询表定义请求")
public class CustomTableQueryRequest {
    
    @Schema(description = "表名称")
    private String tableName;
    
    @Schema(description = "表描述")
    private String tableDescription;
    
    @Schema(description = "空间ID")
    private Long spaceId;
} 