package com.xspaceagi.mcp.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class McpPageQueryDto {

    @Schema(description = "页码")
    private Integer page;

    @Schema(description = "每页数量")
    private Integer pageSize;

    @Schema(description = "关键字搜索")
    private String kw;

    @Schema(description = "空间ID（可选）需要通过空间过滤时有用，不传则返回官方MCP服务")
    private Long spaceId;

    @Schema(description = "创建人ID列表")
    private List<Long> creatorIds;

    @Schema(description = "只返回用户自定义的MCP服务")
    private Boolean justReturnSpaceData;
}
