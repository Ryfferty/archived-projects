package com.xspaceagi.system.spec.utils;

import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.lang.UUID;
import cn.hutool.extra.pinyin.PinyinUtil;

/**
 * 编码生成工具类
 * 用于根据名称自动生成编码
 */
@Slf4j
public class CodeGeneratorUtil {

    /**
     * 根据名称生成编码
     * 规则：
     * 1. 如果名称为空，返回 null
     * 2. 如果名称是纯英文和数字，转换为小写，去除特殊字符，用下划线连接
     * 3. 如果包含中文，转换为全拼（降低重复概率），然后转换为小写，去除特殊字符，用下划线连接
     * 4. 如果生成的编码不是以字母开头，则添加指定前缀
     * 5. 如果编码长度超过100，则截取前100个字符
     *
     * @param name 名称
     * @param prefix 前缀
     * @return 生成的编码
     */
    private static String generateCodeFromName(String name, String prefix) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        String trimmedName = name.trim();
        String code;

        // 判断是否包含中文字符
        if (containsChinese(trimmedName)) {
            // 包含中文，转换为全拼（相比首字母可大幅降低重复概率）
            code = PinyinUtil.getPinyin(trimmedName, "");
        } else {
            // 不包含中文，直接处理英文和数字
            code = trimmedName;
        }

        // 转换为小写
        code = code.toLowerCase();

        // 去除特殊字符，只保留字母、数字和下划线，多个连续的非字母数字字符替换为单个下划线
        code = code.replaceAll("[^a-z0-9_]+", "_");

        // 去除开头和结尾的下划线
        code = code.replaceAll("^_+|_+$", "");

        // 如果为空或不是以字母开头，添加前缀
        if (StringUtils.isBlank(code) || !Character.isLetter(code.charAt(0))) {
            String defaultPrefix = StringUtils.isNotBlank(prefix) ? prefix : "c_";
            code = defaultPrefix + code;
        }

        // 去除连续的下划线
        code = code.replaceAll("_{2,}", "_");

        // 限制长度不超过100
        if (code.length() > 100) {
            code = code.substring(0, 100);
            // 如果截取后以_结尾，去除
            code = code.replaceAll("_+$", "");
        }

        return code;
    }

    /**
     * 检查字符串是否包含中文字符
     *
     * @param str 待检查的字符串
     * @return 如果包含中文字符返回 true，否则返回 false
     */
    private static boolean containsChinese(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符是否为中文字符
     *
     * @param c 待判断的字符
     * @return 如果是中文字符返回 true，否则返回 false
     */
    private static boolean isChinese(char c) {
        // 中文字符的 Unicode 范围
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    /**
     * 生成唯一编码（如果编码已存在，则添加数字后缀）
     * 如果无法生成唯一编码，则使用UUID作为编码
     *
     * @param name 名称
     * @param prefix 前缀
     * @param codeChecker 编码检查函数，用于检查编码是否已存在
     * @return 生成的唯一编码
     */
    public static String generateUniqueCodeFromName(String name, String prefix, Function<String, Boolean> codeChecker) {
        try {
            String baseCode = generateCodeFromName(name, prefix);
            if (StringUtils.isBlank(baseCode)) {
                return generateUUIDCode(prefix);
            }

            if (codeChecker == null || !codeChecker.apply(baseCode)) {
                return baseCode;
            }

            int suffix = 1;
            String uniqueCode;
            do {
                int maxBaseLength = 100 - String.valueOf(suffix).length() - 1;
                String truncatedBase = baseCode.length() > maxBaseLength
                    ? baseCode.substring(0, maxBaseLength)
                    : baseCode;
                uniqueCode = truncatedBase + "_" + suffix;
                suffix++;

                if (suffix > 99) {
                    return generateUUIDCode(prefix);
                }
            } while (codeChecker.apply(uniqueCode));

            return uniqueCode;
        } catch (Exception e) {
            log.error("生成唯一编码失败,取 UUID, name={}, prefix={}", name, prefix, e);
            return generateUUIDCode(prefix);
        }
    }

    /**
     * 生成UUID编码（去除横线，添加指定前缀）
     *
     * @param prefix 前缀
     * @return UUID编码
     */
    private static String generateUUIDCode(String prefix) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String defaultPrefix = StringUtils.isNotBlank(prefix) ? prefix : "c_";
        // 确保以字母开头，添加前缀
        return defaultPrefix + uuid;
    }
}

