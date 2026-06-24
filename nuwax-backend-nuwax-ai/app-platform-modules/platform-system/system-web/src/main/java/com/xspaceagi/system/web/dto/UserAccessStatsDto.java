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
@Schema(description = "用户访问统计响应")
public class UserAccessStatsDto {

    @Schema(description = "今日访问用户数")
    private Long todayUserCount;

    @Schema(description = "七日访问用户数")
    private Long last7DaysUserCount;

    @Schema(description = "30日访问用户数")
    private Long last30DaysUserCount;

    @Schema(description = "七日访问趋势")
    private List<TrendItem> last7DaysTrend;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "趋势数据项")
    public static class TrendItem {
        @Schema(description = "日期/月份")
        private String date;

        @Schema(description = "用户数")
        private Long userCount;
    }

    public static TrendItem fromMap(Map<String, Object> map) {
        return TrendItem.builder()
                .date(map.get("date") != null ? map.get("date").toString() : map.get("month").toString())
                .userCount(Long.valueOf(map.get("user_count").toString()))
                .build();
    }
}