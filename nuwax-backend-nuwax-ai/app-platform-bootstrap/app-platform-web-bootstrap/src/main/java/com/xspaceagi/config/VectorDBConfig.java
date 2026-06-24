package com.xspaceagi.config;

import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Slf4j
@Configuration
public class VectorDBConfig {

    @Value("${milvus.uri}")
    private String milvusUri;

    @Lazy
    @Bean
    public MilvusClientV2 milvusClient() {

        log.info("milvus uri:{}", milvusUri);

        ConnectConfig config = ConnectConfig.builder()
                .uri(milvusUri)
                .build();

        MilvusClientV2 client = new MilvusClientV2(config);
        log.info("milvus client init success, uri:{}", milvusUri);

        return client;
    }

}
