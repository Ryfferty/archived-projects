package com.xspaceagi.log.sdk.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult implements Serializable {

    private Long total;
    private List<SearchResultItem> items;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SearchResultItem {
        private Double score;
        private SearchDocument document;
    }
}
