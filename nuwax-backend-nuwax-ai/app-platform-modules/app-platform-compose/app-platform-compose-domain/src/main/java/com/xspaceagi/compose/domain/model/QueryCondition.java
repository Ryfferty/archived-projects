package com.xspaceagi.compose.domain.model;

import lombok.Data;

/**
 * 查询条件模型
 */
@Data
public class QueryCondition {
    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 操作符
     */
    private String operator;

    /**
     * 字段值
     */
    private Object value;

    /**
     * 连接符（AND/OR）
     */
    private String connector;
} 