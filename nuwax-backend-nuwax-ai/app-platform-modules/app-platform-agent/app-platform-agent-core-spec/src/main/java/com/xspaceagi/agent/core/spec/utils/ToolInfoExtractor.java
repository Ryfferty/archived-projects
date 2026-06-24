package com.xspaceagi.agent.core.spec.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xspaceagi.agent.core.spec.constant.Prompts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ToolInfoExtractor {
    public static List<Object> extractToolInfo(String input) {
        // 正则表达式匹配工具信息或普通文本
        Pattern pattern = Pattern.compile("(<tool_.*>[\\s\\S]*?</tool_.*>)|([^<]+)");
        Matcher matcher = pattern.matcher(input);
        List<Object> output = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                Prompts.ToolUse toolUse = extractToolUse(matcher.group(1).trim());
                output.add(toolUse);
            } else if (matcher.group(2) != null) {
                // 提取普通文本信息
                String plainText = matcher.group(2);
                if (!plainText.isEmpty() && !plainText.contains("```") && !plainText.contains("think>")) {
                    output.add(plainText);
                }
            }
        }
        return output;
    }

    private static Prompts.ToolUse extractToolUse(String text) {
        //兼容模型没有完全按照xml返回的情况
        Pattern pattern = Pattern.compile("<tool_.*>([\\s\\S]*?)</tool_.*>");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String ct = matcher.group(1);
            if (JSON.isValid(ct)) {
                JSONObject jsonObject = JSON.parseObject(ct);
                if (jsonObject.containsKey("name") && jsonObject.containsKey("arguments")) {
                    Prompts.ToolUse toolUse = new Prompts.ToolUse();
                    toolUse.setName(jsonObject.getString("name"));
                    String arguments = trimStr(jsonObject.getString("arguments"));
                    if (StringUtils.isBlank(arguments)) {
                        toolUse.setArguments("{}");
                    } else {
                        toolUse.setArguments(arguments);
                    }
                    return toolUse;
                }
            }
        }

        // 正则表达式匹配工具名称和参数
        pattern = Pattern.compile("<name>([\\s\\S]*?)</name>\\s*<arguments>([\\s\\S]*?)</arguments>");
        Prompts.ToolUse toolUse = extractToolUseWithRegex(text, pattern);
        if (toolUse != null && StringUtils.isBlank(trimStr(toolUse.getArguments()))) {
            toolUse.setArguments("{}");
        }
        if (toolUse == null) {
            pattern = Pattern.compile("<tool>([\\s\\S]*?)</tool>\\s*<arguments>([\\s\\S]*?)</arguments>");
            toolUse = extractToolUseWithRegex(text, pattern);
            if (toolUse != null && StringUtils.isBlank(trimStr(toolUse.getArguments()))) {
                toolUse.setArguments("{}");
            }
        }
        if (toolUse == null) {
            pattern = Pattern.compile("<name>([\\s\\S]*?)</name>\\s*<arguments>([\\s\\S]*?)</tool_.*>");
            toolUse = extractToolUseWithRegex(text, pattern);
            if (toolUse != null) {
                String arguments = trimStr(toolUse.getArguments().trim());
                if (StringUtils.isBlank(arguments)) {
                    toolUse.setArguments("{}");
                }
                if (!JSON.isValid(toolUse.getArguments())) {
                    toolUse = null;
                }
            }
        }
        if (toolUse == null) {
            toolUse = extractToolUse0(text);
        }
        return toolUse;
    }

    private static String trimStr(String arguments) {
        if (arguments == null) {
            return null;
        }
        return arguments.trim();
    }

    private static Prompts.ToolUse extractToolUseWithRegex(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        Prompts.ToolUse toolUse = new Prompts.ToolUse();
        while (matcher.find()) {
            String toolName = matcher.group(1);
            String toolArguments = matcher.group(2);
            toolUse.setName(toolName.trim());
            toolUse.setArguments(toolArguments.trim());
        }
        return StringUtils.isBlank(toolUse.getName()) ? null : toolUse;
    }

    private static Prompts.ToolUse extractToolUse0(String text) {
        try {
            Prompts.ToolUse toolUse = new Prompts.ToolUse();
            // 创建DocumentBuilderFactory实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 将XML字符串转换为输入流
            InputStream inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
            Document document = builder.parse(inputStream);

            // 获取根元素
            Element root = document.getDocumentElement();

            // 提取name标签内容
            NodeList nameList = root.getElementsByTagName("name");
            String name = nameList.item(0).getTextContent();
            toolUse.setName(name);

            // 提取arguments标签内容
            NodeList argumentsList = root.getElementsByTagName("arguments");
            String arguments = argumentsList.item(0).getTextContent();
            toolUse.setArguments(arguments);
            return toolUse;
        } catch (Exception e) {
            log.warn("Error parsing XML", e.getMessage());
        }
        return new Prompts.ToolUse();
    }

    public static String toolNameExchange(String name, String orDefault) {
        if (StringUtils.isBlank(name)) {
            return orDefault;
        }
        //判断是否为英文
        name = name.trim().toLowerCase().replace(" ", "_").replace("__", "_");
        if (name.matches("[a-zA-Z0-9_]+")) {
            return name;
        }
        return orDefault;
    }
}