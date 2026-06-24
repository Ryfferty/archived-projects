package com.xspaceagi.domain.model.valueobj;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志搜索请求包装类
 * 用于包装搜索参数、分页和排序信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentLogSearchRequest {

    /**
     * 查询过滤条件
     */
    private AgentLogSearchParams queryFilter;

    /**
     * 当前页码
     */
    @Builder.Default
    private Long current = 1L;

    /**
     * 每页大小
     */
    @Builder.Default
    private Long pageSize = 10L;

    /**
     * 排序条件
     */
    private List<OrderBy> orders;

    /**
     * 排序条件
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderBy {
        /**
         * 排序字段
         */
        private String column;

        /**
         * 是否升序
         */
        private Boolean asc;
    }
} 