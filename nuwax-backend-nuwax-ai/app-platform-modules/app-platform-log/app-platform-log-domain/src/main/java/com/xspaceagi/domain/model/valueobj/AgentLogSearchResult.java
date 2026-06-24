package com.xspaceagi.domain.model.valueobj;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志分页结果
 * 对应 Rust 的 AgentLogSearchResult 结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AgentLogSearchResult {

    /**
     * 处理耗时(毫秒)
     */
    private Long elapsedTimeMs;

    /**
     * 查询数据列表
     */
    private List<AgentLogEntry> records;

    /**
     * 总数
     */
    private Long total;

    /**
     * 每页显示条数，默认10
     */
    @Builder.Default
    private Long size = 10L;

    /**
     * 当前页
     */
    @Builder.Default
    private Long current = 1L;


} 