package com.xspaceagi.compose.adapter.impl;

import com.xspaceagi.agent.core.sdk.IAgentRpcService;
import com.xspaceagi.agent.core.sdk.dto.AgentInfoDto;
import com.xspaceagi.agent.core.sdk.dto.ReqResult;
import com.xspaceagi.compose.adapter.IAgentRpcServiceAdapter;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AgentRpcServiceAdapter implements IAgentRpcServiceAdapter {


    /**
     * 智能体服务
     */
    @Resource
    private IAgentRpcService agentRpcService;



    @Override
    public List<AgentInfoDto> queryAgentInfoList(List<Long> agentIds) {
        ReqResult<List<AgentInfoDto>> agentListResult = agentRpcService.queryAgentInfoList(agentIds);
        return agentListResult.getData();
    }

    

}
