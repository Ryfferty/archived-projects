package com.xspaceagi.log.sdk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.log.sdk.reponse.AgentLogModelResponse;
import com.xspaceagi.log.sdk.request.AgentLogEntryRequest;
import com.xspaceagi.log.sdk.request.AgentLogSearchParamsRequest;

/**
 * 日志平台的 RPC 服务,用于新增日志
 */
public interface LogPlatformRpcService {

    /**
     * 新增日志
     *
     * @param logEntryRequest
     * @return
     */
    boolean addLog(AgentLogEntryRequest logEntryRequest);

    /**
     * 搜索日志
     *
     * @param searchParams 搜索参数
     * @param current      页码
     * @param pageSize     页大小
     * @return
     */
    IPage<AgentLogModelResponse> searchAgentLogs(AgentLogSearchParamsRequest searchParams,
            Long current,
            Long pageSize);

}
