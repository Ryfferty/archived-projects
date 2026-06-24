package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户统计响应")
public class UserStatsDto {

    @Schema(description = "用户总数")
    private Long totalUserCount;

    @Schema(description = "今日新增用户数")
    private Long todayNewUserCount;

    @Schema(description = "7日新增趋势")
    private List<TrendItem> last7DaysTrend;

    @Schema(description = "30日新增趋势")
    private List<TrendItem> last30DaysTrend;

    @Schema(description = "按月新增趋势")
    private List<TrendItem> monthlyTrend;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "趋势数据项")
    public static class TrendItem {
        @Schema(description = "日期/月份")
        private String date;

        @Schema(description = "新增用户数")
        private Long userCount;
    }

    public static TrendItem fromMap(Map<String, Object> map) {
        return TrendItem.builder()
                .date(map.get("date") != null ? map.get("date").toString() : map.get("month").toString())
                .userCount(Long.valueOf(map.get("user_count").toString()))
                .build();
    }
}