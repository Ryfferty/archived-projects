package com.xspaceagi.agent.web.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TriggerTimeZoneData implements Serializable {

    @Schema(description = "UTC时区列表")
    private List<UTCTimeZone> utcTimeZones;

    @Schema(description = "时间范围列表")
    private List<CronExpScope> cronExpScopes;

    /**
     * {
     * "utc": "UTC-02:00",
     * "timeZones": [
     * {
     * "timeZone": "America/Noronha",
     * "name": "费尔南多·德·诺罗尼亚时间"
     * },
     * {
     * "timeZone": "Atlantic/South_Georgia",
     * "name": "南乔治亚岛时间"
     * }
     * ]
     * }
     */
    @Data
    public static class TimeZone {

        @Schema(description = "时区")
        private String timeZone;

        @Schema(description = "时区名称")
        private String name;
    }

    @Data
    public static class UTCTimeZone {

        @Schema(description = "UTC时区")
        private String utc;

        private List<TimeZone> timeZones;
    }

    /**
     * {
     * "scope": "每周触发",
     * "cronExps": [
     * {
     * "day": "星期一",
     * "expression": "0 0 0 ? * MON"
     * },
     * {
     * "day": "星期二",
     * "expression": "0 0 0 ? * TUE"
     * },
     * {
     * "day": "星期三",
     * "expression": "0 0 0 ? * WED"
     * },
     * {
     * "day": "星期四",
     * "expression": "0 0 0 ? * THU"
     * },
     * {
     * "day": "星期五",
     * "expression": "0 0 0 ? * FRI"
     * },
     * {
     * "day": "星期六",
     * "expression": "0 0 0 ? * SAT"
     * },
     * {
     * "day": "星期日",
     * "expression": "0 0 0 ? * SUN"
     * }
     * ]
     * }
     */

    @Data
    public static class CronExp {

        @Schema(description = "时间描述")
        private String time;

        @Schema(description = "表达式")
        private String expression;
    }

    @Data
    public static class CronExpScope {

        @Schema(description = "时间范围")
        private String scope;

        private List<CronExp> cronExps;
    }
}
