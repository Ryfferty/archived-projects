package com.xspaceagi.compose.sdk.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 分页查询doris表结构定义
 */
@Schema(description = "分页查询doris表结构定义")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryDorisTableDefinePageRequest {

    /**
     * 空间ID
     */
    @Schema(description = "空间ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonPropertyDescription("空间ID")
    private Long spaceId;


    /**
     * 关键字搜索
     */
    @Schema(description = "关键字搜索")
    @JsonPropertyDescription("关键字搜索")
    private String kw;

    /**
     * 页码
     */
    @Schema(description = "页码", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonPropertyDescription("页码")
    private Integer pageNo;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonPropertyDescription("每页条数")
    private Integer pageSize;

    @Schema(description = "空间ID列表,查询用户有权限的空间,限制访问空间,比如工作流查询全部知识库,要限制用户有权限的空间下的知识库")
    @JsonPropertyDescription("空间ID列表,查询用户有权限的空间,限制访问空间,比如工作流查询全部知识库,要限制用户有权限的空间下的知识库")
    private List<Long> authSpaceIds;

    @Schema(description = "创建人ID列表")
    @JsonPropertyDescription("创建人ID列表")
    private List<Long> creatorIds;

}
