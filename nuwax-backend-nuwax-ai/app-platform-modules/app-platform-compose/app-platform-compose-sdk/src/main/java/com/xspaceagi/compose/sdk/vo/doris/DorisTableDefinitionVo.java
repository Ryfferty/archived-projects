package com.xspaceagi.compose.sdk.vo.doris;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Doris表定义
 */
@Data
@Schema(description = "Doris表定义")
public class DorisTableDefinitionVo {
    
    @Schema(description = "数据库名")
    @JsonPropertyDescription("数据库名")
    private String database;
    
    @Schema(description = "表名")
    @JsonPropertyDescription("表名")
    private String table;

    @Schema(description = "图标图片地址")
    private String icon;
    
    @Schema(description = "表注释")
    @JsonPropertyDescription("表注释")
    private String comment;
    
    @Schema(description = "表引擎")
    @JsonPropertyDescription("表引擎")
    private String engine;
    
    @Schema(description = "分桶数")
    @JsonPropertyDescription("分桶数")
    private Integer buckets;
    
    @Schema(description = "副本数")
    @JsonPropertyDescription("副本数")
    private Integer replicationNum;
    
    @Schema(description = "分布键")
    @JsonPropertyDescription("分布键")
    private List<String> distributedKeys;
    
    @Schema(description = "重复键")
    @JsonPropertyDescription("重复键")
    private List<String> duplicateKeys;
    
    @Schema(description = "字段定义列表")
    @JsonPropertyDescription("字段定义列表")
    private List<DorisTableFieldVo> fields;
    
    @Schema(description = "索引定义列表")
    @JsonPropertyDescription("索引定义列表")
    private List<DorisTableIndexVo> indexes;
    
    @Schema(description = "表属性")
    @JsonPropertyDescription("表属性")
    private Map<String, String> properties;
    
    @Schema(description = "原始建表DDL")
    @JsonPropertyDescription("原始建表DDL")
    private String createTableDdl;
} 