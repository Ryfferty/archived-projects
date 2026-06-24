package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubagentDto implements Serializable {

    @Schema(description = "名称，非必传，如果不传则从content中提取name")
    private String name;

//    @Schema(description = "描述")
//    private String description;

    @Schema(description = "内容")
    private String content;
}
