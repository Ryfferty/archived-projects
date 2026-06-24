package com.xspaceagi.agent.web.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationStatsDto {
    private Long totalConversations;
    private Long todayNewConversations;
    private List<TrendItem> last7DaysTrend;
    private List<TrendItem> last30DaysTrend;
    private List<TrendItem> monthlyTrend;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrendItem {
        private String date;
        private Long conversationCount;
    }
}