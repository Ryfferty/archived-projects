package com.xspaceagi.system.spec.config;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * EventBus配置类
 * 用于管理Guava EventBus实例
 */
@Slf4j
@Configuration
public class EventBusConfig {

    @Bean
    public EventBus eventBus() {
        EventBus eventBus = new EventBus();
        log.info("EventBus配置初始化完成");
        return eventBus;
    }
} 