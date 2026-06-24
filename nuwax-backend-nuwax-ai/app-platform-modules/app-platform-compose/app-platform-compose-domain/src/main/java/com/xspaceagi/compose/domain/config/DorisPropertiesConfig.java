package com.xspaceagi.compose.domain.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableConfigurationProperties(DorisProperties.class)
public class DorisPropertiesConfig {
    // 空类，仅用于激活 DorisProperties 的配置绑定

}