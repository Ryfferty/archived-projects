package com.xspaceagi.compose.sdk.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * doris 表数据查询响应
 */
@Schema(description = "doris 表数据查询响应")
@Getter
@Setter
public class DorisTableDataResponse implements Serializable {
    /**
     * 表结构定义
     */
    @Schema(description = "表结构定义")
    @JsonPropertyDescription("表结构定义")
    private TableDefineVo tableDefineVo;

    /**
     * 数据
     */
    @Schema(description = "数据")
    @JsonPropertyDescription("数据")
    private List<Map<String, Object>> data;

    /**
     * 影响的数据行数
     */
    @Schema(description = "影响的数据行数")
    @JsonPropertyDescription("影响的数据行数")
    private Long rowNum;

    /**
     * 数据主键id,新增sql会有对应的id
     * <p>
     * 注意: 新增sql会有对应的id,查询sql没有;且新增 insert语句必须是单条插入
     * </p>
     */
    @Schema(description = "数据主键id,新增sql会有对应的id")
    @JsonPropertyDescription("数据主键id,新增sql会有对应的id")
    private Long rowId;
}
