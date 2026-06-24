package com.xspaceagi.file.infra.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置
 */
@Configuration
@MapperScan("com.xspaceagi.file.infra.dao.mapper")
public class MyBatisConfig {
}
