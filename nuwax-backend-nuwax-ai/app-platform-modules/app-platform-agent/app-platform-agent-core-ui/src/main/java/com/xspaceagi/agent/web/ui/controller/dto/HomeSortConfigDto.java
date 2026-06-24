package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HomeSortConfigDto {

    @Schema(description = "分类列表，按顺序排，可选，传递时需要传完整的列表")
    private List<String> types;

    @Schema(description = "分类下的智能体ID列表，按顺序排，可选，可传某个分类下的所有id列表")
    private Map<String, List<Long>> typeAgentIds;
}
