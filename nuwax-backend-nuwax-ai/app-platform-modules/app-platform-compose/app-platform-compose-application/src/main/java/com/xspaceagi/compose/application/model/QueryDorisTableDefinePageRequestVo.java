package com.xspaceagi.compose.application.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.xspaceagi.compose.sdk.request.QueryDorisTableDefinePageRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页查询doris表结构定义
 */
@Schema(description = "分页查询doris表结构定义")
@Getter
@Setter
@Builder
public class QueryDorisTableDefinePageRequestVo {

    /**
     * 空间ID
     */
    @Schema(description = "空间ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonPropertyDescription("空间ID")
    private Long spaceId;

    @Schema(description = "空间ID列表,查询用户有权限的空间,限制访问空间,比如工作流查询全部知识库,要限制用户有权限的空间下的知识库")
    private List<Long> authSpaceIds;

    /**
     * 创建人ID列表
     */
    @Schema(description = "创建人ID列表")
    private List<Long> creatorIds;

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;

    /**
     * 转换为QueryDorisTableDefinePageRequest
     *
     * @param request 查询请求
     * @return 查询请求
     */
    public static QueryDorisTableDefinePageRequestVo convert(QueryDorisTableDefinePageRequest request) {
        return QueryDorisTableDefinePageRequestVo.builder()
                .spaceId(request.getSpaceId())
                .authSpaceIds(request.getAuthSpaceIds())
                .creatorIds(request.getCreatorIds())
                .tableName(request.getKw())
                .build();
    }

}
