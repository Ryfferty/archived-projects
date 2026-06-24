package com.xspaceagi.system.spec.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 支持泛型 List 的 JSON TypeHandler
 */
public abstract class ListJsonTypeHandler<T> extends BaseTypeHandler<List<T>> {

    private static final ObjectMapper objectMapper;
    
    /**
     * 最大序列化大小限制：100MB
     * 超过此大小会抛出明确的异常，而不是导致 OOM
     */
    private static final long MAX_SERIALIZATION_SIZE = 100 * 1024 * 1024L;
    
    /**
     * 最大列表元素数量限制：10000
     * 超过此数量会抛出明确的异常
     */
    private static final int MAX_LIST_SIZE = 10000;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        // 配置 StreamReadConstraints 以支持更大的字符串长度
        // 设置最大字符串长度为 100MB (100 * 1024 * 1024 字符)
        StreamReadConstraints constraints = StreamReadConstraints.builder()
                .maxStringLength(100 * 1024 * 1024)
                .build();
        JsonFactory factory = objectMapper.getFactory();
        factory.setStreamReadConstraints(constraints);
    }

    /**
     * 子类需要提供 TypeReference 以支持正确的泛型反序列化
     */
    protected abstract TypeReference<List<T>> getTypeReference();

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<T> list, JdbcType jdbcType)
            throws SQLException {
        if (Objects.nonNull(list)) {
            // 检查列表大小
            if (list.size() > MAX_LIST_SIZE) {
                throw new SQLException(String.format(
                    "列表大小超过限制: %d > %d，无法序列化。请减少数据量或分批处理。",
                    list.size(), MAX_LIST_SIZE));
            }
            
            try {
                // 使用 ByteArrayOutputStream 来限制内存使用并检查大小
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try (JsonGenerator generator = objectMapper.getFactory().createGenerator(outputStream)) {
                    objectMapper.writeValue(generator, list);
                }
                
                // 检查序列化后的大小
                byte[] bytes = outputStream.toByteArray();
                if (bytes.length > MAX_SERIALIZATION_SIZE) {
                    throw new SQLException(String.format(
                        "序列化后的数据大小超过限制: %d bytes > %d bytes (%.2f MB)。请减少数据量或分批处理。",
                        bytes.length, MAX_SERIALIZATION_SIZE, bytes.length / (1024.0 * 1024.0)));
                }
                
                String jsonString = outputStream.toString("UTF-8");
                // 确保 JSON 字符串以正确的字符集传递给 MySQL
                preparedStatement.setObject(i, jsonString, java.sql.Types.VARCHAR);
            } catch (JsonProcessingException e) {
                throw new SQLException("序列化失败: " + e.getMessage(), e);
            } catch (IOException e) {
                throw new SQLException("序列化IO失败: " + e.getMessage(), e);
            } catch (OutOfMemoryError e) {
                throw new SQLException(String.format(
                    "序列化时内存溢出。列表大小: %d，请减少数据量或增加JVM堆内存。", list.size()), e);
            }
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String json = resultSet.getString(columnName);
        if (json != null && !json.isEmpty()) {
            try {
                return objectMapper.readValue(json, getTypeReference());
            } catch (JsonProcessingException e) {
                throw new SQLException("反序列化失败: columnName=" + columnName + ", json=" + json, e);
            }
        }
        return null;
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String json = resultSet.getString(columnIndex);
        if (json != null && !json.isEmpty()) {
            try {
                return objectMapper.readValue(json, getTypeReference());
            } catch (JsonProcessingException e) {
                throw new SQLException("反序列化失败: columnIndex=" + columnIndex + ", json=" + json, e);
            }
        }
        return null;
    }

    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String json = callableStatement.getString(columnIndex);
        if (json != null && !json.isEmpty()) {
            try {
                return objectMapper.readValue(json, getTypeReference());
            } catch (JsonProcessingException e) {
                throw new SQLException("反序列化失败: columnIndex=" + columnIndex + ", json=" + json, e);
            }
        }
        return null;
    }
}
