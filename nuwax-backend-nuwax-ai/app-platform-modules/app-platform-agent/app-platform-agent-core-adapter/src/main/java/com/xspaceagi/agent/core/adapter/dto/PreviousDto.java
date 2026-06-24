package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreviousDto implements Serializable {

    @Schema(description = "所有上级节点列表")
    private List<PreviousNodeDto> previousNodes;

    @Schema(description = "循环节点内部节点列表")
    private List<PreviousNodeDto> innerPreviousNodes;

    @Schema(description = "所有上级节点的输出参数Map")
    private Map<String, Arg> argMap;
}
