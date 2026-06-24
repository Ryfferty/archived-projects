package com.xspaceagi.mcp.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.mcp.adapter.repository.entity.McpConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface McpConfigMapper extends BaseMapper<McpConfig> {
}
