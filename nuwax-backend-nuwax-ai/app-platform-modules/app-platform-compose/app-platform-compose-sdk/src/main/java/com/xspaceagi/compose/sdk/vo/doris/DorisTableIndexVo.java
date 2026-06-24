package com.xspaceagi.compose.sdk.vo.doris;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Doris表索引定义
 */
@Data
@Schema(description = "Doris表索引定义")
public class DorisTableIndexVo {
    
    @Schema(description = "索引名称")
    @JsonPropertyDescription("索引名称")
    private String indexName;
    
    @Schema(description = "索引类型")
    @JsonPropertyDescription("索引类型")
    private String indexType;
    
    @Schema(description = "索引列")
    @JsonPropertyDescription("索引列")
    private String indexColumns;
    
    @Schema(description = "索引注释")
    @JsonPropertyDescription("索引注释")
    private String comment;
    
    @Schema(description = "是否唯一索引")
    @JsonPropertyDescription("是否唯一索引")
    private Boolean isUnique;
} 