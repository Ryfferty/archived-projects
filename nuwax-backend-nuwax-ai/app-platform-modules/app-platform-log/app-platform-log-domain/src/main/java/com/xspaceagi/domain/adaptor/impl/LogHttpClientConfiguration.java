package com.xspaceagi.domain.adaptor.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 日志 HTTP 客户端配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "log-module.log.rust-service")
public class LogHttpClientConfiguration {

    /**
     * Rust 服务的基础 URL（必填）
     */
    private String baseUrl;

    /**
     * 连接超时时间（秒）
     */
    private Integer connectTimeoutSeconds = 10;

    /**
     * 写入超时时间（秒）
     */
    private Integer writeTimeoutSeconds = 30;

    /**
     * 读取超时时间（秒）
     */
    private Integer readTimeoutSeconds = 30;

    /**
     * 是否启用连接池
     */
    private Boolean enableConnectionPool = true;

    /**
     * 最大空闲连接数
     */
    private Integer maxIdleConnections = 5;

    /**
     * 连接保持存活时间（分钟）
     */
    private Integer keepAliveDurationMinutes = 5;
} 