package com.xspaceagi.system.spec.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonSerializeUtil {

    // 包含类型信息的ObjectMapper
    private static ObjectMapper objectMapper;
    
    // 不包含类型信息的ObjectMapper
    private static ObjectMapper objectMapperWithoutType;

    private static final LinkedHashMap<String, String> PACKAGE_MAP = new LinkedHashMap<String, String>() {{
        put("com.xspaceagi.platform.ui.web.dto", "com.xspaceagi.system.web.dto");
        put("com.xspaceagi.domain.log", "com.xspaceagi.system.domain.log");
        put("com.xspaceagi.platform", "com.xspaceagi.system");
    }};
    
    static {
        objectMapper = JacksonBuilder.builder(new JacksonProperties()).withJavaType().build();
        objectMapperWithoutType = JacksonBuilder.builder(new JacksonProperties()).build();
    }

    /**
     * 反序列化JSON字符串为对象，包含类型信息（@class字段）
     */
    public static Object parseObjectGeneric(String json) {
        if (json == null) {
            return null;
        }
        
        try {
            return objectMapper.readValue(json, Object.class);
        } catch (IOException e) {
            try {
                String newJson = json;
                for (Map.Entry<String, String> entry : PACKAGE_MAP.entrySet()) {
                    newJson = newJson.replace(entry.getKey(), entry.getValue());
                }
                return objectMapper.readValue(newJson, Object.class);
            } catch (IOException retryException) {
                throw new RuntimeException("反序列化失败，原始异常: " + e.getMessage() + 
                                           ", 迁移后异常: " + retryException.getMessage(), e);
            }
        }
    }

    /**
     * 序列化对象为JSON字符串，包含类型信息（@class字段）
     */
    public static String toJSONStringGeneric(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化对象为JSON字符串，不包含类型信息（@class字段）
     */
    public static String toJSONString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return objectMapperWithoutType.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 反序列化JSON字符串为指定类型的对象，用于不包含类型信息的JSON
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapperWithoutType.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 反序列化JSON字符串为指定类型的对象，用于不包含类型信息的JSON（支持泛型）
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapperWithoutType.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("反序列化失败: " + e.getMessage(), e);
        }
    }

    public static Object deepCopy(Object obj) {
        return parseObjectGeneric(toJSONStringGeneric(obj));
    }
}
