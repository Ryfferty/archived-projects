package com.xspaceagi.agent.core.spec.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Markdown 字段提取工具类
 */
public class MarkdownExtractUtil {

    /**
     * 提取字段值
     */
    public static String extractFieldValue(String markdownContent, String fieldName) {
        if (markdownContent == null || markdownContent.isBlank() || fieldName == null || fieldName.isBlank()) {
            return null;
        }

        // 尝试解析 YAML front matter
        String yamlFrontMatter = extractYamlFrontMatter(markdownContent);
        if (yamlFrontMatter != null) {
            String fieldValue = extractYamlField(yamlFrontMatter, fieldName);
            // 如果字段存在（即使值为空），直接返回，不再尝试其他格式
            if (fieldValue != null) {
                return fieldValue.trim();
            }
        }

        // 尝试其他格式：fieldName: xxx 或 "fieldName": "xxx" 或 fieldName = xxx
        String fieldValue = extractFieldByPattern(markdownContent, fieldName);
        return fieldValue != null && !fieldValue.isBlank() ? fieldValue.trim() : null;
    }

    /**
     * 提取 YAML front matter 部分（--- 之间的内容）
     */
    private static String extractYamlFrontMatter(String content) {
        if (content == null || content.isBlank()) {
            return null;
        }

        String trimmed = content.trim();
        if (!trimmed.startsWith("---")) {
            return null;
        }

        // 找到第一个 --- 之后的内容
        int firstDashIndex = trimmed.indexOf("---");
        if (firstDashIndex < 0) {
            return null;
        }

        // 找到第二个 ---
        int secondDashIndex = trimmed.indexOf("---", firstDashIndex + 3);
        if (secondDashIndex < 0) {
            return null;
        }

        // 提取两个 --- 之间的内容
        return trimmed.substring(firstDashIndex + 3, secondDashIndex).trim();
    }

    /**
     * 从 YAML 内容中提取指定字段的值
     */
    private static String extractYamlField(String yamlContent, String fieldName) {
        if (yamlContent == null || yamlContent.isBlank() || fieldName == null || fieldName.isBlank()) {
            return null;
        }

        String[] lines = yamlContent.split("\n");
        for (String line : lines) {
            line = line.trim();
            // 精确匹配字段名，确保字段名后面紧跟冒号（可能有空格）
            // 使用正则表达式避免误匹配，例如：name: 应该匹配，但 name2: 不应该匹配 name:
            String pattern = "^" + Pattern.quote(fieldName) + "\\s*:";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(line);
            if (matcher.find()) {
                // 提取冒号后面的值
                int colonIndex = line.indexOf(':');
                if (colonIndex >= 0) {
                    String value = line.substring(colonIndex + 1).trim();
                    // 移除可能的引号
                    if ((value.startsWith("\"") && value.endsWith("\"")) ||
                            (value.startsWith("'") && value.endsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }
                    // 返回值（可能为空字符串，表示字段存在但值为空）
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 使用正则表达式从内容中提取字段值
     * 支持多种格式：fieldName: value, "fieldName": "value", fieldName = value
     */
    private static String extractFieldByPattern(String content, String fieldName) {
        if (content == null || content.isBlank() || fieldName == null || fieldName.isBlank()) {
            return null;
        }

        // 尝试匹配 name: value 格式（YAML 风格）
        Pattern pattern1 = Pattern.compile(
                "(?i)" + fieldName + "\\s*:\\s*([^\\n\\r]+)", Pattern.MULTILINE);
        Matcher matcher1 = pattern1.matcher(content);
        if (matcher1.find()) {
            String value = matcher1.group(1).trim();
            // 移除引号
            if ((value.startsWith("\"") && value.endsWith("\"")) ||
                    (value.startsWith("'") && value.endsWith("'"))) {
                value = value.substring(1, value.length() - 1);
            }
            return value;
        }

        // 尝试匹配 "name": "value" 格式（JSON 风格）
        Pattern pattern2 = Pattern.compile(
                "(?i)\"" + fieldName + "\"\\s*:\\s*\"([^\"]+)\"", Pattern.MULTILINE);
        Matcher matcher2 = pattern2.matcher(content);
        if (matcher2.find()) {
            return matcher2.group(1);
        }

        // 尝试匹配 name = value 格式
        Pattern pattern3 = Pattern.compile(
                "(?i)" + fieldName + "\\s*=\\s*([^\\n\\r]+)", Pattern.MULTILINE);
        Matcher matcher3 = pattern3.matcher(content);
        if (matcher3.find()) {
            String value = matcher3.group(1).trim();
            // 移除引号
            if ((value.startsWith("\"") && value.endsWith("\"")) ||
                    (value.startsWith("'") && value.endsWith("'"))) {
                value = value.substring(1, value.length() - 1);
            }
            return value;
        }

        return null;
    }
}