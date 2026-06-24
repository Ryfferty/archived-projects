package com.xspaceagi.config;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.xspaceagi.log.spec.CustomLocalDateTimeSerializer;
import com.xspaceagi.log.spec.FlexibleDateTimeDeserializer;

/**
 * UTC 时间序列化配置
 */
@Configuration
public class UTCDateTimeConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {

            // 使用 SimpleModule 统一注册序列化器和反序列化器
            SimpleModule module = new SimpleModule();

            // 注册自定义序列化器
            module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());

            // 注册自定义反序列化器
            module.addDeserializer(LocalDateTime.class, new FlexibleDateTimeDeserializer());

            builder.modules(module);
            
            // 配置 StreamReadConstraints 以支持更大的字符串长度
            // 设置最大字符串长度为 100MB (100 * 1024 * 1024 字符)，用于支持大文件内容
            builder.postConfigurer(objectMapper -> {
                StreamReadConstraints constraints = StreamReadConstraints.builder()
                        .maxStringLength(100 * 1024 * 1024)
                        .build();
                objectMapper.getFactory().setStreamReadConstraints(constraints);
            });
        };
    }

}
