package com.xspaceagi.memory.app.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.xspaceagi.memory.app.service.MemoryApplicationService;
import com.xspaceagi.memory.app.service.MemoryUnitService;
import com.xspaceagi.memory.sdk.dto.MemoryMetaData;
import com.xspaceagi.memory.sdk.dto.MemorySaveDto;
import com.xspaceagi.memory.sdk.dto.MemoryUnitDTO;
import com.xspaceagi.memory.sdk.dto.MemoryUnitQueryDTO;
import com.xspaceagi.memory.sdk.service.IMemoryRpcService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemoryRpcServiceImpl implements IMemoryRpcService {

    @Resource
    private MemoryApplicationService memoryApplicationService;

    @Resource
    private MemoryUnitService memoryUnitService;

    @Override
    public void createMemory(MemoryMetaData memoryData) {
        try {
            log.debug("createMemory: {}", memoryData);
            memoryApplicationService.createMemory(memoryData);
        } catch (Exception e) {
            log.error("createMemory error: {}", e.getMessage(), e);
            // 由异常处理接管
        }
    }

    @Override
    public String searchMemoriesMd(Long tenantId, Long userId, Long agentId, String inputMessage, String context, boolean justKeywordSearch, boolean filterSensitive) {
        log.debug("searchMemoriesMd: {}, {}, {}, {}, {}", tenantId, userId, inputMessage, context, justKeywordSearch);
        List<MemoryUnitDTO> memoryUnitDTOS = memoryApplicationService.searchMemories(tenantId, userId, agentId, inputMessage, context, justKeywordSearch);
        return toMd(memoryUnitDTOS, filterSensitive);
    }

    private String toMd(List<MemoryUnitDTO> memoryUnitDTOS, boolean filterSensitive) {
        StringBuilder sb = new StringBuilder();
        memoryUnitDTOS.forEach(memoryUnitDTO -> {
            if (filterSensitive && memoryUnitDTO.getIsSensitive()) {
                return;
            }
            JSONObject jsonObject = JSONObject.parseObject(memoryUnitDTO.getContentJson());
            JSONObject keyValues = jsonObject.getJSONObject("keyValues");
            String category = memoryUnitDTO.getCategory();//md的一级标题
            String subCategory = memoryUnitDTO.getSubCategory();// md的二级标题
            sb.append("# ").append(category).append("\n");
            sb.append("## ").append(subCategory).append("\n");
            keyValues.forEach((key, value) -> sb.append("- ").append(key).append(": ").append(value).append("\n"));
            sb.append("- memory_id: ").append(memoryUnitDTO.getId()).append("\n");
            sb.append("- update_time: ").append(memoryUnitDTO.getModified()).append("\n");
            sb.append("\n");
        });
        return sb.toString();
    }

    @Override
    public String queryMemoriesMd(MemoryUnitQueryDTO memoryUnitQueryDTO, boolean filterSensitive) {
        List<MemoryUnitDTO> memoryUnitDTOS = memoryUnitService.queryList(memoryUnitQueryDTO);
        return toMd(memoryUnitDTOS, filterSensitive);
    }

    @Override
    public void saveMemories(Long tenantId, Long userId, Long agentId, MemorySaveDto memorySaveDto) {
        memoryApplicationService.saveMemories(tenantId, userId, agentId, memorySaveDto);
    }

    @Override
    public void deleteMemories(Long tenantId, Long userId, List<Long> memoryIds) {
        memoryApplicationService.deleteMemories(tenantId, userId, memoryIds);
    }
}
