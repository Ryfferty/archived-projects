package com.xspaceagi.compose.sdk.request;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 查询用户可以使用的数据表组件的,对应的表结构定义信息
 */
@Schema(description = "查询用户可以使用的数据表组件的,对应的表结构定义信息")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DorisToolTableDefineRequest {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonPropertyDescription("用户ID")
    private Long userId;

    /**
     * 空间ID
     */
    @Schema(description = "空间ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonPropertyDescription("空间ID")
    private Long spaceId;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonPropertyDescription("租户ID")
    private Long tenantId;
}
