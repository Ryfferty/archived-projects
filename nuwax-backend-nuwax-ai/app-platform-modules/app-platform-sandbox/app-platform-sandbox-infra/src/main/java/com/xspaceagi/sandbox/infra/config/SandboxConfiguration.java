package com.xspaceagi.sandbox.infra.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Sandbox 模块配置类
 */
@Configuration
@EnableConfigurationProperties({
    ReverseServerProperties.class
})
public class SandboxConfiguration {
    // 配置类，用于启用配置属性扫描
}
