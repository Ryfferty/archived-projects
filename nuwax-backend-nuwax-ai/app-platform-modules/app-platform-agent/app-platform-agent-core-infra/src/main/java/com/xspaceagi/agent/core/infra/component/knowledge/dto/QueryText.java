package com.xspaceagi.agent.core.infra.component.knowledge.dto;

import com.xspaceagi.agent.core.spec.enums.SearchStrategyEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryText {

    private String text;
    private SearchStrategyEnum searchStrategy;
}
