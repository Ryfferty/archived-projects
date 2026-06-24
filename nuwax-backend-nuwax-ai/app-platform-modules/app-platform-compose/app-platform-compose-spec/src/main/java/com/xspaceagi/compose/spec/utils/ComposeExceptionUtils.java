package com.xspaceagi.compose.spec.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 组件异常处理工具
 */
@Slf4j
public class ComposeExceptionUtils {

    // 数据库错误匹配正则表达式
    private static final Pattern DATA_TOO_LONG_PATTERN = Pattern.compile(
            "Data truncation: Data too long for column '([^']+)' at row (\\d+)",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern DUPLICATE_ENTRY_PATTERN = Pattern.compile(
            "Duplicate entry '([^']+)' for key '([^']+)'",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern CONSTRAINT_VIOLATION_PATTERN = Pattern.compile(
            "cannot be null|Column '([^']+)' cannot be null",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern INVALID_DECIMAL_PATTERN = Pattern.compile(
            "Out of range value for column '([^']+)' at row (\\d+)",
            Pattern.CASE_INSENSITIVE);

    /**
     * 获取根错误信息
     *
     * @param e 异常
     * @return 根错误信息
     */
    public static String getRootErrorMessage(Throwable e) {
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }

    /**
     * 获取用户友好的错误信息
     * 将技术性的数据库错误转换为易于理解的提示
     *
     * @param e 异常
     * @return 用户友好的错误信息
     */
    public static String getUserFriendlyErrorMessage(Throwable e) {
        String rootMessage = getRootErrorMessage(e);
        if (rootMessage == null || rootMessage.trim().isEmpty()) {
            return "数据处理失败";
        }

        // 尝试解析和转换常见的数据库错误
        String friendlyMessage = parseDataTooLongError(rootMessage);
        if (friendlyMessage != null) {
            return friendlyMessage;
        }

        friendlyMessage = parseDuplicateEntryError(rootMessage);
        if (friendlyMessage != null) {
            return friendlyMessage;
        }

        friendlyMessage = parseConstraintViolationError(rootMessage);
        if (friendlyMessage != null) {
            return friendlyMessage;
        }

        friendlyMessage = parseInvalidDecimalError(rootMessage);
        if (friendlyMessage != null) {
            return friendlyMessage;
        }

        // 如果无法识别特定错误类型，返回简化但有用的信息
        return extractMeaningfulError(rootMessage);
    }

    /**
     * 解析"数据过长"错误
     */
    private static String parseDataTooLongError(String errorMessage) {
        Matcher matcher = DATA_TOO_LONG_PATTERN.matcher(errorMessage);
        if (matcher.find()) {
            String columnName = matcher.group(1);
            String rowNumber = matcher.group(2);
            return String.format("第%s行字段\"%s\"内容过长，请减少字符数量",
                    rowNumber, columnName);
        }
        return null;
    }

    /**
     * 解析"重复条目"错误
     */
    private static String parseDuplicateEntryError(String errorMessage) {
        Matcher matcher = DUPLICATE_ENTRY_PATTERN.matcher(errorMessage);
        if (matcher.find()) {
            String duplicateValue = matcher.group(1);
            return String.format("数据\"%s\"已存在，请修改为其他值", duplicateValue);
        }
        return null;
    }

    /**
     * 解析"约束违反"错误（如非空约束）
     */
    private static String parseConstraintViolationError(String errorMessage) {
        Matcher matcher = CONSTRAINT_VIOLATION_PATTERN.matcher(errorMessage);
        if (matcher.find()) {
            String columnName = matcher.group(1);
            if (columnName != null) {
                return String.format("字段\"%s\"为必填项，请填写内容", columnName);
            } else {
                return "存在必填字段未填写，请检查并补充";
            }
        }
        return null;
    }

    /**
     * 解析"数值超出范围"错误
     */
    private static String parseInvalidDecimalError(String errorMessage) {
        Matcher matcher = INVALID_DECIMAL_PATTERN.matcher(errorMessage);
        if (matcher.find()) {
            String columnName = matcher.group(1);
            String rowNumber = matcher.group(2);
            return String.format("第%s行字段\"%s\"数值过大，请填写较小的数字",
                    rowNumber, columnName);
        }
        return null;
    }

    /**
     * 提取有意义的错误信息
     * 保留关键信息，去掉技术细节
     */
    private static String extractMeaningfulError(String errorMessage) {
        // 移除技术前缀和无用信息
        String cleaned = errorMessage
                .replaceAll("^(java\\.lang\\.|com\\.mysql\\.|org\\.springframework\\.|com\\.mysql\\.cj\\.).*?:", "")
                .replaceAll(
                        "^(SQLException|DataAccessException|RuntimeException|MySQLIntegrityConstraintViolationException):",
                        "")
                .replaceAll("^.*?Exception:", "")
                .replaceAll("\\b(MySQL|Doris|JDBC|SQL|database|table|constraint|varchar|decimal|int|bigint)\\b", "")
                .replaceAll("\\s+", " ")
                .trim();

        // 如果清理后还有有用信息且不包含过多技术术语，保留它
        if (cleaned.length() >= 15 && !containsOnlyTechnicalJargon(cleaned)) {
            // 进一步优化常见的错误信息
            cleaned = optimizeCommonErrors(cleaned);
            return cleaned;
        }

        // 否则返回通用但有指导意义的提示
        return "数据格式不正确，请检查输入内容";
    }

    /**
     * 优化常见错误信息
     */
    private static String optimizeCommonErrors(String message) {
        String lower = message.toLowerCase();

        if (lower.contains("too long") || lower.contains("length")) {
            return "内容长度超出限制，请缩短文本";
        }
        if (lower.contains("duplicate") || lower.contains("already exists")) {
            return "数据重复，请修改为不同的值";
        }
        if (lower.contains("null") || lower.contains("empty")) {
            return "必填字段未填写，请补充完整";
        }
        if (lower.contains("format") || lower.contains("invalid")) {
            return "数据格式错误，请检查并修正";
        }
        if (lower.contains("number") || lower.contains("numeric")) {
            return "数字格式错误，请输入有效数字";
        }
        if (lower.contains("date") || lower.contains("time")) {
            return "日期格式错误，请使用正确的日期格式";
        }

        return message;
    }

    /**
     * 检查是否只包含技术术语
     */
    private static boolean containsOnlyTechnicalJargon(String message) {
        String[] technicalOnlyTerms = {
                "syntax error", "connection", "timeout", "deadlock", "transaction",
                "foreign key", "primary key", "index", "timestamp", "datetime",
                "rollback", "commit", "lock", "session", "driver"
        };

        String lowerMessage = message.toLowerCase();
        for (String term : technicalOnlyTerms) {
            if (lowerMessage.contains(term)) {
                return true;
            }
        }
        return false;
    }
}
