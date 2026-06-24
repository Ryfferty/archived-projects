package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增节点,前端传的节点配置
 */
@Data
public class AddNodeConfigDto implements Serializable {


    @Schema(description = "知识库配置", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<KnowledgeNodeConfigDto.KnowledgeBaseConfigDto> knowledgeBaseConfigs;

    @Schema(description = "MCP工具名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String toolName;

}
