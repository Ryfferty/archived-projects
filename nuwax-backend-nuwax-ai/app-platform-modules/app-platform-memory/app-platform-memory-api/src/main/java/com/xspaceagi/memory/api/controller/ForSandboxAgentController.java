package com.xspaceagi.memory.api.controller;

import com.xspaceagi.memory.api.dto.MemoryUnitQuery;
import com.xspaceagi.memory.sdk.dto.MemoryDeleteDto;
import com.xspaceagi.memory.sdk.dto.MemorySaveDto;
import com.xspaceagi.memory.sdk.dto.MemoryUnitQueryDTO;
import com.xspaceagi.memory.sdk.service.IMemoryRpcService;
import com.xspaceagi.system.sdk.service.dto.UserAccessKeyDto;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForSandboxAgentController {

    @Resource
    private IMemoryRpcService iMemoryRpcService;

    @PostMapping("/api/v1/4sandbox/memory/query")
    public ReqResult<String> query(@RequestBody MemoryUnitQuery memoryUnitQuery) {
        UserAccessKeyDto userAccessKey = (UserAccessKeyDto) RequestContext.get().getUserAccessKey();
        MemoryUnitQueryDTO memoryUnitQueryDTO = new MemoryUnitQueryDTO();
        memoryUnitQueryDTO.setTenantId(RequestContext.get().getTenantId());
        memoryUnitQueryDTO.setUserId(RequestContext.get().getUserId());
        memoryUnitQueryDTO.setAgentId(Long.parseLong(userAccessKey.getTargetId()));
        memoryUnitQueryDTO.setCategories(memoryUnitQuery.getCategories());
        memoryUnitQueryDTO.setSubCategories(memoryUnitQuery.getSubCategories());
        memoryUnitQueryDTO.setTags(memoryUnitQuery.getTags());
        memoryUnitQueryDTO.setQueryType(memoryUnitQuery.getTimeRangeType());
        memoryUnitQueryDTO.setTimeRange(memoryUnitQuery.getTimeRange());
        memoryUnitQueryDTO.setLimit(memoryUnitQuery.getLimit() == null ? 10 : memoryUnitQuery.getLimit());
        String memory = iMemoryRpcService.queryMemoriesMd(memoryUnitQueryDTO, memoryUnitQuery.isFilterSensitive());
        return ReqResult.success(memory);
    }


    @PostMapping("/api/v1/4sandbox/memory/save")
    public ReqResult<String> save(@RequestBody MemorySaveDto memorySaveDto) {
        UserAccessKeyDto userAccessKey = (UserAccessKeyDto) RequestContext.get().getUserAccessKey();
        iMemoryRpcService.saveMemories(RequestContext.get().getTenantId(), RequestContext.get().getUserId(), Long.parseLong(userAccessKey.getTargetId()), memorySaveDto);
        return ReqResult.success();
    }

    @PostMapping("/api/v1/4sandbox/memory/delete")
    public ReqResult<String> delete(@RequestBody MemoryDeleteDto memoryDeleteDto) {
        iMemoryRpcService.deleteMemories(RequestContext.get().getTenantId(), RequestContext.get().getUserId(), memoryDeleteDto.getMemoryIds());
        return ReqResult.success();
    }
}
