package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import com.xspaceagi.agent.core.adapter.dto.config.plugin.PluginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 插件节点配置
 */
@Getter
@Setter
public class PluginNodeConfigDto extends NodeConfigDto {

    private Long pluginId;

    @Schema(description = "插件配置", hidden = true)
    private PluginDto pluginConfig;
}
