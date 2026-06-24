package com.xspaceagi.agent.core.adapter.dto.config.bind;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SubAgentBindConfigDto {

    @Schema(description = "子代理列表")
    private List<SubAgent> subAgents;

    @Data
    public static class SubAgent {
        @Schema(description = "子代理名称")
        private String name;
        @Schema(description = "子代理提示词")
        private String prompt;
    }
}
