package com.xspaceagi.memory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 记忆领域模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memory {

    /**
     * 记忆ID
     */
    private String memoryId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 记忆类型 (short_term, long_term, working)
     */
    private String memoryType;

    /**
     * 记忆内容
     */
    private String content;

    /**
     * 扩展元数据
     */
    private Map<String, Object> metadata;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 记忆状态 (active, archived, deleted)
     */
    private String status;

    /**
     * 重要性评分 (0.0-1.0)
     */
    private Double importanceScore;

    /**
     * 访问次数
     */
    private Integer accessCount;

    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessTime;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 检查记忆是否过期
     */
    public boolean isExpired() {
        return expireTime != null && LocalDateTime.now().isAfter(expireTime);
    }

    /**
     * 检查记忆是否活跃
     */
    public boolean isActive() {
        return "active".equals(status) && !isExpired();
    }

    /**
     * 增加访问次数
     */
    public void incrementAccessCount() {
        this.accessCount = (this.accessCount == null ? 0 : this.accessCount) + 1;
        this.lastAccessTime = LocalDateTime.now();
    }
}
