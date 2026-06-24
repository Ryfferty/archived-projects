package com.xspaceagi.system.sdk.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时段工具类，用于生成各时段类型的当前时段值
 */
public class PeriodUtils {

    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("yyyy");
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");

    private PeriodUtils() {
    }

    /**
     * 获取当前年份时段值
     * 示例: 2026
     */
    public static String getCurrentYear() {
        return LocalDate.now().format(YEAR_FORMATTER);
    }

    /**
     * 获取当前月份时段值
     * 示例: 202601
     */
    public static String getCurrentMonth() {
        return LocalDate.now().format(MONTH_FORMATTER);
    }

    /**
     * 获取当前日期时段值
     * 示例: 20260129
     */
    public static String getCurrentDay() {
        return LocalDate.now().format(DAY_FORMATTER);
    }

    /**
     * 获取当前小时时段值
     * 示例: 2026012912
     */
    public static String getCurrentHour() {
        return LocalDateTime.now().format(HOUR_FORMATTER);
    }

    /**
     * 根据时段类型获取当前时段值
     *
     * @param periodType 时段类型（YEAR/MONTH/DAY/HOUR）
     * @return 时段值
     */
    public static String getCurrentPeriod(String periodType) {
        return switch (periodType) {
            case PeriodType.YEAR -> getCurrentYear();
            case PeriodType.MONTH -> getCurrentMonth();
            case PeriodType.DAY -> getCurrentDay();
            case PeriodType.HOUR -> getCurrentHour();
            default -> throw new IllegalArgumentException("Unknown period type: " + periodType);
        };
    }

    /**
     * 获取指定时间的年份时段值
     */
    public static String getYear(LocalDateTime dateTime) {
        return dateTime.format(YEAR_FORMATTER);
    }

    /**
     * 获取指定时间的月份时段值
     */
    public static String getMonth(LocalDateTime dateTime) {
        return dateTime.format(MONTH_FORMATTER);
    }

    /**
     * 获取指定时间的日期时段值
     */
    public static String getDay(LocalDateTime dateTime) {
        return dateTime.format(DAY_FORMATTER);
    }

    /**
     * 获取指定时间的小时时段值
     */
    public static String getHour(LocalDateTime dateTime) {
        return dateTime.format(HOUR_FORMATTER);
    }
}
