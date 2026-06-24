package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ToolDto implements Serializable {

     @Schema(description =  "工具ID")
    private Long toolId;

     @Schema(description =  "工具唯一标识")
    private String toolKey;

     @Schema(description =  "工具名称")
    private String name;

     @Schema(description =  "工具图标")
    private String iconUrl;

     @Schema(description =  "工具描述")
    private String description;

     @Schema(description =  "工具实现类")
    private String handlerClazz;
}
