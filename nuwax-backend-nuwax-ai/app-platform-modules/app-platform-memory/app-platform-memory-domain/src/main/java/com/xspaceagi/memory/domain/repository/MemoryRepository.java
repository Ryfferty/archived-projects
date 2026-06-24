package com.xspaceagi.memory.domain.repository;

import com.xspaceagi.memory.domain.model.Memory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 记忆仓储接口
 */
public interface MemoryRepository {

    /**
     * 保存记忆
     */
    Memory save(Memory memory);

    /**
     * 根据ID查找记忆
     */
    Optional<Memory> findById(String memoryId);

    /**
     * 根据用户ID和记忆类型查找记忆列表
     */
    List<Memory> findByUserIdAndType(String userId, String memoryType);

    /**
     * 根据会话ID查找记忆列表
     */
    List<Memory> findBySessionId(String sessionId);

    /**
     * 查找过期的记忆
     */
    List<Memory> findExpiredMemories(LocalDateTime currentTime);

    /**
     * 根据重要性分数查找记忆
     */
    List<Memory> findByImportanceScoreGreaterThan(Double minScore);

    /**
     * 删除记忆
     */
    void deleteById(String memoryId);

    /**
     * 批量删除记忆
     */
    void batchDelete(List<String> memoryIds);

    /**
     * 更新记忆状态
     */
    void updateStatus(String memoryId, String status);

    /**
     * 统计用户记忆数量
     */
    long countByUserIdAndType(String userId, String memoryType);
}
