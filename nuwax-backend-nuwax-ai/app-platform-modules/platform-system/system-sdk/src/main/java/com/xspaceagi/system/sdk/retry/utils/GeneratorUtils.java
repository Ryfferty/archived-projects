package com.xspaceagi.system.sdk.retry.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class GeneratorUtils {

    private static final String group_connector = "___";

    private static final MD5 md5Digester = MD5.create();

    private String projectCode;
    private String appCode;

    public static String generateGroup(String projectCode, String appCode) {
        return projectCode + group_connector + appCode;
    }

    public String generateGroup() {
        return projectCode + group_connector + appCode;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public synchronized static String md5(String... strs) {
        String origin = Optional.ofNullable(strs)
            .map(Arrays::stream)
            .orElse(Stream.empty())
            .collect(Collectors.joining(","));
        String result = md5Digester.digestHex(origin);
        md5Digester.reset();
        return result;
    }

    public static String replaceLineBreak(String str) {
        return replaceLineBreak(str, "<br>");
    }

    public static String replaceLineBreak(String str, String target) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.replaceAll("\\\\r\\\\n\\\\tat", target)
            .replaceAll("\\\\n\\\\tat", target)
            .replaceAll("\\\\n", target);
    }

    public static String subString(String origin, int length) {
        if (StringUtils.isEmpty(origin)) {
            return origin;
        }
        origin = replaceLineBreak(origin, Strings.EMPTY);
        if (origin.length() <= length) {
            return origin;
        }
        return origin.substring(0, length) + "...";
    }

}
