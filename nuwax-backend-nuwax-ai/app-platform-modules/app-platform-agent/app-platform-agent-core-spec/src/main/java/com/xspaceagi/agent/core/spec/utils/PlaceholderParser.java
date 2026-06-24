package com.xspaceagi.agent.core.spec.utils;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderParser {

    public static List<String> extractPlaceholders(String input) {
        List<String> placeholders = new ArrayList<>();
        String regex = "\\{\\{(.*?)\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            placeholders.add(matcher.group(1));
        }

        return placeholders;
    }

    private static Object resolvePlaceholder(List<Object> results, String placeholder) {
        // 去掉占位符中的 "R" 并将剩余部分分割成各个层级
        String[] keys = placeholder.split("\\.");
        // 从 results 的末尾开始遍历
        for (int i = results.size() - 1; i >= 0; i--) {
            Object currentValue = results.get(i);

            for (String key : keys) {
                if (StringUtils.isBlank(key)) {
                    continue;
                }
                currentValue = getValue(currentValue, key);
                if (currentValue == null) {
                    break; // 如果某一层级的值为null，跳出内层循环继续遍历下一个 result
                }
            }

            if (currentValue != null) {
                return currentValue; // 一旦找到非空值，立即返回
            }
        }
        return null; // 如果没有找到匹配的 key 或值为 null，返回 null
    }

    private static Object getValue(Object currentValue, String key) {
        if (currentValue == null) {
            return null;
        }
        if (key.startsWith("[")) {
            // 处理列表索引，例如 "[0]"
            int index;
            try {
                index = Integer.parseInt(key.substring(1, key.indexOf("]")));
            } catch (NumberFormatException e) {
                return null;
            }
            if (currentValue instanceof List<?>) {
                if (index >= ((List<?>) currentValue).size()) {
                    return null;
                }
                currentValue = ((List<?>) currentValue).get(index);
            } else {
                return null; // 如果期望的是 List 但结果不是，返回 null
            }
        } else {
            // 处理 Map 键，可能带有列表索引，例如 "someKey[0]"
            if (key.contains("[")) {
                String mapKey = key.substring(0, key.indexOf("["));
                int index;
                try {
                    index = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
                } catch (NumberFormatException e) {
                    return null;
                }
                if (currentValue instanceof Map<?, ?>) {
                    currentValue = ((Map<String, Object>) currentValue).get(mapKey);
                    if (currentValue instanceof List<?>) {
                        if (index >= ((List<?>) currentValue).size()) {
                            return null;
                        }
                        currentValue = ((List<?>) currentValue).get(index);
                    } else {
                        return null; // 如果期望的是 List 但结果不是，返回 null
                    }
                } else {
                    return null; // 如果当前值不是 Map，返回 null
                }
            } else {
                if (currentValue instanceof Map<?, ?>) {
                    currentValue = ((Map<String, Object>) currentValue).get(key);
                } else {
                    return null; // 如果当前值不是 Map，返回 null
                }
            }
        }
        return currentValue;
    }

    public static String parseString(Object arg) {
        if (arg == null) {
            return null;
        }
        if (arg instanceof String) {
            return (String) arg;
        }
        return JSONObject.toJSONString(arg);
    }

    public static String resoleAndReplacePlaceholder(Object obj, String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        List<String> keys = extractPlaceholders(input);
        for (String key : keys) {
            String[] subKeys = key.split("\\.");
            Object currentValue = obj;
            for (String subKey : subKeys) {
                currentValue = getValue(currentValue, subKey);
                if (currentValue == null) {
                    break;
                }
            }
            String replaceStr = parseString(currentValue);
            if (replaceStr == null) {
                replaceStr = "";
            }
            input = input.replace("{{" + key + "}}", replaceStr);
        }
        return input;
    }

    public static String resoleAndReplacePlaceholder(Object obj, String input, String join) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        List<String> keys = extractPlaceholders(input);
        for (String key : keys) {
            String[] subKeys = key.split("\\.");
            Object currentValue = obj;
            for (String subKey : subKeys) {
                currentValue = getValue(currentValue, subKey);
                if (currentValue == null) {
                    break;
                }
            }
            String value = listToString(currentValue, join);
            if (value == null) {
                value = "";
            }
            input = input.replace("{{" + key + "}}", value);
        }
        return input;
    }

    private static String listToString(Object currentValue, String join) {
        //将转义过的恢复
        try {
            join = StringEscapeUtils.unescapeJava(join);
        } catch (Exception e) {
            //  忽略异常
        }
        if (currentValue instanceof List<?>) {
            if (CollectionUtils.isNotEmpty((List<?>) currentValue)) {
                if (((List<?>) currentValue).get(0) instanceof List<?>) {
                    List<String> strList = new ArrayList<>();
                    for (Object list : (List<?>) currentValue) {
                        strList.add(listToString(list, join));
                    }
                    return Joiner.on(join).join(strList);
                } else {
                    return Joiner.on(join).join((List<?>) currentValue);
                }
            }
        }
        return parseString(currentValue);
    }
}