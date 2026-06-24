package com.xspaceagi.system.infra.db.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.xspaceagi.**.infra.dao.mapper"})
public class MysqlConfig {

}
