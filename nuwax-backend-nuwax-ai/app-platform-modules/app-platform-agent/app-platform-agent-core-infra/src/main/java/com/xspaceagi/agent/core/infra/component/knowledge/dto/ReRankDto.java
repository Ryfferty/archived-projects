package com.xspaceagi.agent.core.infra.component.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class ReRankDto {

    @JsonPropertyDescription("相关问题的问答ID排序结果，与输入的qaId对应，相关性高的排在前面")
    private List<Long> qaIds;
}
