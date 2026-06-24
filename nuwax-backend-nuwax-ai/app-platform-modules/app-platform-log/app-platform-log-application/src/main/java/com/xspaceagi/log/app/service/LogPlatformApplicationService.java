package com.xspaceagi.log.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.domain.model.AgentLogModel;
import com.xspaceagi.domain.model.valueobj.AgentLogEntry;
import com.xspaceagi.domain.model.valueobj.AgentLogSearchParams;
import com.xspaceagi.domain.service.ILogPlatformDomainService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志平台应用服务
 */
@Slf4j
@Service
public class LogPlatformApplicationService {

    @Resource
    private ILogPlatformDomainService logPlatformDomainService;

    /**
     * 搜索智能体日志
     */
    public IPage<AgentLogModel> searchAgentLogs(AgentLogSearchParams searchParams,
            Long current,
            Long pageSize) {
        // 调用领域服务
        return logPlatformDomainService.searchAgentLogs(searchParams, current, pageSize);
    }

    /**
     * 详情
     */
    public AgentLogModel queryOneAgentLog(AgentLogSearchParams searchParams) {
        return logPlatformDomainService.queryOneAgentLog(searchParams);
    }

    /**
     * 新增单个智能体日志
     */
    public boolean addAgentLog(AgentLogEntry logEntry) {
        return logPlatformDomainService.addAgentLog(logEntry);
    }

    /**
     * 批量新增智能体日志
     */
    public boolean batchAddAgentLogs(List<AgentLogEntry> logEntries) {
        return logPlatformDomainService.batchAddAgentLogs(logEntries);
    }

    /**
     * 健康检查
     */
    public boolean healthCheck() {
        return logPlatformDomainService.healthCheck();
    }
}