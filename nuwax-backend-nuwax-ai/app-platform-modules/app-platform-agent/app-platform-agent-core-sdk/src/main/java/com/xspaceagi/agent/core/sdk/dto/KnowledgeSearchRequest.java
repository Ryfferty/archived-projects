package com.xspaceagi.agent.core.sdk.dto;

import com.xspaceagi.agent.core.spec.enums.SearchStrategyEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class KnowledgeSearchRequest implements Serializable {

    private String requestId;

    private String query;

    private SearchStrategyEnum searchStrategy;

    private Integer maxRecallCount;

    private Double matchingDegree;

    private List<Long> knowledgeBaseIds;

    private Object user;

    public Double getMatchingDegree() {
        return matchingDegree == null ? 0.5 : matchingDegree;
    }

    public enum SearchStrategy {
        //语义
        SEMANTIC,
        //混合
        MIXED,
        //全文
        FULL_TEXT
    }
}