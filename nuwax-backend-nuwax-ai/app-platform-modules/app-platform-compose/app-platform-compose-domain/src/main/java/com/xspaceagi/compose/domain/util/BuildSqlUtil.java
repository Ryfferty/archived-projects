package com.xspaceagi.compose.domain.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * sql 构建工具
 */
@NoArgsConstructor
@Slf4j
public class BuildSqlUtil {

    /**
     * 构建插入值字符串
     */
    public static String buildInsertValues(Map<String, Object> rowData) {
        return rowData.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    return "`" + escapeSqlString(key) + "` = " + formatInsertValue(value);
                })
                .collect(Collectors.joining(", "));
    }

    /**
     * 格式化插入值
     */
    private static String formatInsertValue(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return "'" + escapeSqlString((String) value) + "'";
        }
        if (value instanceof Number) {
            return value.toString();
        }
        if (value instanceof Boolean) {
            return ((Boolean) value) ? "1" : "0";
        }
        if (value instanceof Date) {
            return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value) + "'";
        }
        if (value instanceof LocalDateTime) {
            return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value) + "'";
        }
        return value.toString();
    }

    /**
     * 转义 SQL 字符串中的特殊字符 (简单实现)
     * 主要处理单引号和反斜杠
     * 
     * @param value 需要转义的字符串
     * @return 转义后的字符串
     */
    public static String escapeSqlString(String value) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        // 替换反斜杠为双反斜杠，替换单引号为反斜杠+单引号
        // Java String literal: \ becomes \\, ' becomes \'
        return value.replace("\\", "\\\\").replace("'", "\\'");
    }

    /**
     * 构建更新值字符串
     */
    public static String buildUpdateValues(Map<String, Object> rowData) {
        return rowData.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    return "`" + escapeSqlString(key) + "` = " + formatInsertValue(value);
                })
                .collect(Collectors.joining(", "));
    }

}
