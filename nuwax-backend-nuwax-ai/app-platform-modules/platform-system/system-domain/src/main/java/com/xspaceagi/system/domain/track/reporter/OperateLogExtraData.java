package com.xspaceagi.system.domain.track.reporter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 操作日志额外数据
 * 用于同时记录 HTTP 请求参数和 SpEL 表达式提取的参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateLogExtraData {

    /**
     * HTTP 请求参数（从 request.getParameterMap() 获取）
     */
    private Map<String, String> httpParams;

    /**
     * SpEL 表达式提取的业务参数
     */
    private Object spelData;

    /**
     * SpEL 表达式（用于记录使用了哪个表达式）
     */
    private String spelExpression;
}
