package com.xspaceagi.mcp.adapter.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.mcp.adapter.dto.McpPageQueryDto;
import com.xspaceagi.mcp.adapter.repository.entity.McpConfig;

import java.util.List;

public interface McpConfigDomainService {

    void add(McpConfig mcpConfig);

    void delete(Long id);

    void update(McpConfig mcpConfig);

    McpConfig getById(Long id);

    McpConfig getBySpaceIdAndUid(Long spaceId, String uid);

    List<McpConfig> queryListBySpaceId(Long spaceId);

    Page<McpConfig> queryDeployedMcpList(McpPageQueryDto mcpPageQueryDto);

    Page<McpConfig> queryDeployedMcpListForManage(McpPageQueryDto mcpPageQueryDto);

    IPage<McpConfig> queryPage(McpConfig entityFilter, int pageNum, int pageSize);

    List<McpConfig> queryDeployedSSEMcpConfigList(Long lastId, int size);

    /**
     * 统计 MCP 总数
     *
     * @return MCP 总数
     */
    Long countTotalMcps();
}
