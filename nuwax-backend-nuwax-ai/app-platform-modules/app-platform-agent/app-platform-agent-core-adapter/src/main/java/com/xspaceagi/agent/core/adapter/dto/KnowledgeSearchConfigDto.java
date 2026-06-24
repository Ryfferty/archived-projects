package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.dto.config.bind.KnowledgeBaseBindConfigDto;
import com.xspaceagi.agent.core.spec.enums.SearchStrategyEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeSearchConfigDto implements Serializable {

    private SearchStrategyEnum searchStrategy;

    private Integer maxRecallCount;

    private Double matchingDegree;

    private KnowledgeBaseBindConfigDto.NoneRecallReplyTypeEnum noneRecallReplyType;

    private String noneRecallReply;
}
