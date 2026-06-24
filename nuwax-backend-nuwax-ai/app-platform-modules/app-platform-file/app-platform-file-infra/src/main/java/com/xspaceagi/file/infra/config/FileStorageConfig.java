package com.xspaceagi.file.infra.config;

import com.xspaceagi.file.domain.storage.FileStorageStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件存储配置
 */
@Configuration
public class FileStorageConfig {

    @Bean
    public Map<String, FileStorageStrategy> storageStrategyMap(List<FileStorageStrategy> strategies) {
        return strategies.stream()
                .collect(Collectors.toMap(
                        FileStorageStrategy::getStorageType,
                        strategy -> strategy
                ));
    }
}
