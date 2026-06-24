package com.xspaceagi.system.infra.dao.typehandler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xspaceagi.system.sdk.service.dto.TokenLimit;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * TokenLimit 类型 TypeHandler
 */
public class TokenLimitTypeHandler extends BaseTypeHandler<TokenLimit> {

    private static final ObjectMapper objectMapper;
    
    /**
     * 最大序列化大小限制：1MB
     * 超过此大小会抛出明确的异常，而不是导致 OOM
     */
    private static final long MAX_SERIALIZATION_SIZE = 1 * 1024 * 1024L;

    static {
        objectMapper = new ObjectMapper();
        // 配置不包含类型信息
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

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TokenLimit tokenLimit, JdbcType jdbcType)
            throws SQLException {
        if (Objects.nonNull(tokenLimit)) {
            try {
                // 使用 ByteArrayOutputStream 来限制内存使用并检查大小
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try (JsonGenerator generator = objectMapper.getFactory().createGenerator(outputStream)) {
                    objectMapper.writeValue(generator, tokenLimit);
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
                throw new SQLException("序列化时内存溢出，请减少数据量或增加JVM堆内存。", e);
            }
        }
    }

    @Override
    public TokenLimit getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String json = resultSet.getString(s);
        if (json != null && !json.isEmpty()) {
            try {
                return objectMapper.readValue(json, TokenLimit.class);
            } catch (JsonProcessingException e) {
                throw new SQLException("反序列化失败: columnName=" + s + ", json=" + json, e);
            }
        }
        return null;
    }

    @Override
    public TokenLimit getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String json = resultSet.getString(i);
        if (json != null && !json.isEmpty()) {
            try {
                return objectMapper.readValue(json, TokenLimit.class);
            } catch (JsonProcessingException e) {
                throw new SQLException("反序列化失败: columnIndex=" + i + ", json=" + json, e);
            }
        }
        return null;
    }

    @Override
    public TokenLimit getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String json = callableStatement.getString(i);
        if (json != null && !json.isEmpty()) {
            try {
                return objectMapper.readValue(json, TokenLimit.class);
            } catch (JsonProcessingException e) {
                throw new SQLException("反序列化失败: columnIndex=" + i + ", json=" + json, e);
            }
        }
        return null;
    }
}
