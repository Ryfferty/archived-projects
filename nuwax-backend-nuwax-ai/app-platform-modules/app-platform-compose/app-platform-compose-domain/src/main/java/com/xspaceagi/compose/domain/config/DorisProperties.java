package com.xspaceagi.compose.domain.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Doris 配置属性
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.doris.table")
public class DorisProperties {

    /**
     * 副本数
     * 生产环境通常为3，开发环境可以为1
     */
    private Integer replicationNum; // 默认值为3

    /**
     * 分桶数（BUCKETS）
     */
    private Integer bucketNum; // 默认值为10

    /**
     * 重复键配置
     * 如果为空，则使用第一个字段作为重复键
     */
    private String duplicateKey;

    /**
     * 分布键配置
     * 如果为空，则使用第一个字段作为分布键
     */
    private String distributedKey;

}