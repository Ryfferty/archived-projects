package com.xspaceagi.mcp.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.mcp.adapter.repository.McpConfigRepository;
import com.xspaceagi.mcp.adapter.repository.entity.McpConfig;
import com.xspaceagi.mcp.infra.dao.mapper.McpConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class McpConfigRepositoryImpl extends ServiceImpl<McpConfigMapper, McpConfig> implements McpConfigRepository {
}
