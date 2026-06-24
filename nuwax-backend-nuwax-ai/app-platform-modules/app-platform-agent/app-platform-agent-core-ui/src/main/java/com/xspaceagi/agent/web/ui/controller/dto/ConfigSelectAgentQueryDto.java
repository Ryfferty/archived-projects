package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigSelectAgentQueryDto implements Serializable {

    @Schema(description = "关键字搜索")
    private String kw;
}
