package com.xspaceagi.compose.sdk.request;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 查询doris表结构定义
 */
@Schema(description = "查询doris表结构定义")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DorisTableDefineRequest {

    /**
     * 表ID
     */
    @Schema(description = "表ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonPropertyDescription("表ID")
    private Long tableId;


}
