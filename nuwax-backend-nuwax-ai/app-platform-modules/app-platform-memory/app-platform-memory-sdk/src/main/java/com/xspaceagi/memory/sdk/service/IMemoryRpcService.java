package com.xspaceagi.memory.sdk.service;

import com.xspaceagi.memory.sdk.dto.MemoryMetaData;
import com.xspaceagi.memory.sdk.dto.MemorySaveDto;
import com.xspaceagi.memory.sdk.dto.MemoryUnitQueryDTO;

import java.util.List;

public interface IMemoryRpcService {

    void createMemory(MemoryMetaData memoryData);

    String searchMemoriesMd(Long tenantId, Long userId, Long agentId, String inputMessage, String context, boolean justKeywordSearch, boolean filterSensitive);

    String queryMemoriesMd(MemoryUnitQueryDTO memoryUnitQueryDTO, boolean filterSensitive);

    void saveMemories(Long tenantId, Long userId, Long agentId, MemorySaveDto memorySaveDto);

    void deleteMemories(Long tenantId, Long userId, List<Long> memoryIds);
}
