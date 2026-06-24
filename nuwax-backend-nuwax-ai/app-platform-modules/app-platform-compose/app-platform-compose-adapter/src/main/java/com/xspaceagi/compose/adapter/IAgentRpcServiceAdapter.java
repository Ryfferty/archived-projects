package com.xspaceagi.compose.adapter;

import com.xspaceagi.agent.core.sdk.dto.AgentInfoDto;

import java.util.List;
/**
 * 智能体服务适配器
 */
public interface IAgentRpcServiceAdapter {




    /**
     * 查询智能体信息列表
     * @param agentIds 智能体id列表
     * @return 智能体信息列表
     */
    public List<AgentInfoDto> queryAgentInfoList(List<Long> agentIds);

}
