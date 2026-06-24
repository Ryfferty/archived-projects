package com.xspaceagi.eco.market.domain.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 服务器发布配置查询请求DTO
 */
@Data
@Schema(description = "服务器发布配置查询请求DTO")
public class ServerPublishConfigQueryRequest {

    /**
     * 名称，模糊查询
     */
    @Schema(description = "名称，模糊查询")
    private String name;

    /**
     * 市场类型,1:插件;2:模板;3:MCP
     */
    @Schema(description = "市场类型,1:插件;2:模板;3:MCP")
    private Integer dataType;

    /**
     * 细分类型,比如: 插件,智能体,工作流
     */
    @Schema(description = "细分类型,比如: 插件,智能体,工作流")
    private String targetType;

    /**
     * 分类编码
     */
    @Schema(description = "分类编码")
    private String categoryCode;

    /**
     * 分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回
     */
    @Schema(description = "分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回")
    private Integer shareStatus;

    /**
     * 使用状态,1:启用;2:禁用;
     */
    @Schema(description = "使用状态,1:启用;2:禁用;")
    private Integer useStatus;

    /**
     * 作者信息，模糊查询
     */
    @Schema(description = "作者信息，模糊查询")
    private String author;
}