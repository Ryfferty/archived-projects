package com.xspaceagi.domain.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.domain.model.AgentLogModel;
import com.xspaceagi.domain.model.valueobj.AgentLogEntry;
import com.xspaceagi.domain.model.valueobj.AgentLogSearchParams;

/**
 * 日志平台领域服务接口
 */
public interface ILogPlatformDomainService {
    
    /**
     * 搜索智能体日志
     * 
     * @param searchParams 搜索参数
     * @param current 当前页
     * @param pageSize 每页大小
     * @return 分页日志结果
     */
    IPage<AgentLogModel> searchAgentLogs(AgentLogSearchParams searchParams, Long current, Long pageSize);


    /**
     * 详情
     * 
     * @param searchParams 搜索参数
     * @param current 当前页
     * @param pageSize 每页大小
     * @return 日志详情
     */
    AgentLogModel queryOneAgentLog(AgentLogSearchParams searchParams);
    
    /**
     * 新增单个智能体日志
     * 
     * @param logEntry 日志条目
     * @return 是否成功
     */
    boolean addAgentLog(AgentLogEntry logEntry);
    
    /**
     * 批量新增智能体日志
     * 
     * @param logEntries 日志条目列表
     * @return 是否成功
     */
    boolean batchAddAgentLogs(List<AgentLogEntry> logEntries);
    
    /**
     * 健康检查
     * 
     * @return Rust 服务是否健康
     */
    boolean healthCheck();
}
