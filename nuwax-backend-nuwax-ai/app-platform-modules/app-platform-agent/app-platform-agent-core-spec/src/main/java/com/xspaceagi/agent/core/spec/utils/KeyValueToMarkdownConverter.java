package com.xspaceagi.agent.core.spec.utils;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * KeyValue对象数组转Markdown工具类
 */
public class KeyValueToMarkdownConverter {

    /**
     * 将对象数组转换为Markdown表格
     *
     * @param objects   对象数组
     * @param tableName 表格名称（可选）
     * @return Markdown格式的表格字符串
     */
    public static String convertToMarkdown(List<Object> objects, String tableName) {
        if (objects == null || objects.isEmpty()) {
            return "";
        }

        // 获取所有字段名
        Set<String> headers = getAllHeaders(objects);

        StringBuilder markdown = new StringBuilder();

        // 添加表格名称
        if (tableName != null && !tableName.trim().isEmpty()) {
            markdown.append("### ").append(tableName).append("\n\n");
        }

        // 构建表头
        markdown.append("|");
        for (String header : headers) {
            markdown.append(header).append("|");
        }
        markdown.append("\n");

        // 构建分隔线
        markdown.append("|");
        for (int i = 0; i < headers.size(); i++) {
            markdown.append("---|");
        }
        markdown.append("\n");

        // 构建数据行
        for (Object obj : objects) {
            markdown.append("|");
            for (String header : headers) {
                String value = getFieldValue(obj, header);
                markdown.append(value).append("|");
            }
            markdown.append("\n");
        }

        return markdown.toString();
    }

    /**
     * 获取对象数组中所有唯一的字段名
     */
    private static Set<String> getAllHeaders(List<Object> objects) {
        Set<String> headers = new LinkedHashSet<>();

        for (Object obj : objects) {
            if (obj != null) {
                Class<?> clazz = obj.getClass();
                // 如果是Map类型，获取所有key
                if (obj instanceof java.util.Map) {
                    java.util.Map<?, ?> map = (java.util.Map<?, ?>) obj;
                    headers.addAll(map.keySet().stream()
                            .map(key -> key.toString())
                            .toList());
                } else {
                    // 获取所有字段（包括私有字段）
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        headers.add(field.getName());
                    }
                }
            }
        }

        return headers;
    }

    /**
     * 获取对象指定字段的值
     */
    private static String getFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            return "";
        }

        try {
            // 处理Map类型
            if (obj instanceof java.util.Map) {
                java.util.Map<?, ?> map = (java.util.Map<?, ?>) obj;
                Object value = map.get(fieldName);
                return value != null ? value.toString() : "";
            }

            // 处理普通对象
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(obj);
            return value != null ? value.toString() : "";

        } catch (NoSuchFieldException e) {
            // 字段不存在，返回空字符串
            return "";
        } catch (IllegalAccessException e) {
            // 访问权限问题，返回空字符串
            return "";
        }
    }
}