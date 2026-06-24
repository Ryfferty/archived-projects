package com.xspaceagi.agent.core.infra.component.knowledge;

import com.xspaceagi.agent.core.infra.component.agent.AgentContext;
import com.xspaceagi.agent.core.spec.enums.SearchStrategyEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchContext implements Serializable {

    private String requestId;

    private String query;

    private SearchStrategyEnum searchStrategy;

    private Integer maxRecallCount;

    private Double matchingDegree;

    private List<Long> knowledgeBaseIds;

    private AgentContext agentContext;

    public Double getMatchingDegree() {
        return matchingDegree == null ? 0.5 : matchingDegree;
    }
}
