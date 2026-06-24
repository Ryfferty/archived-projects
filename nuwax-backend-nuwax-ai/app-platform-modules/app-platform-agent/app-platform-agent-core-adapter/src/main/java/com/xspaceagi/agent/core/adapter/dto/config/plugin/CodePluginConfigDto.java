package com.xspaceagi.agent.core.adapter.dto.config.plugin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CodePluginConfigDto extends PluginConfigDto {

    @Schema(description = "代码")
    private String code;
}
