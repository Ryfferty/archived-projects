package com.xspaceagi.compose.sdk.request;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 查询doris表数据
 */
@Schema(description = "查询doris表数据")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DorisTableDataRequest {

    /**
     * 表ID
     */
    @Schema(description = "表ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonPropertyDescription("表ID")
    private Long tableId;

    /**
     * sql
     */
    @Schema(description = "sql")
    @JsonPropertyDescription("sql")
    private String sql;

    /**
     * 绑定sql的参数,比如: a = 1, b = 2,用于替换sql中的参数绑定
     */
    @Schema(description = "绑定sql的参数")
    @JsonPropertyDescription("绑定sql的参数")
    private Map<String, Object> args;

    /**
     * 扩展参数,比如:uid = 123, spaceId = 122,用于额外限制条件,加入到查询sql语句中
     */
    @Schema(description = "扩展参数,比如:uid = 123, spaceId = 122,用于额外限制条件,加入到查询sql语句中")
    private Map<String, Object> extArgs;

}
