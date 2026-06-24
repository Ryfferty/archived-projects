package com.xspaceagi.agent.core.adapter.application;

import com.xspaceagi.agent.core.adapter.dto.AgentTempChatDto;

import java.util.List;

public interface AgentTempChatApplicationService {

    /**
     * 创建临时链接
     *
     * @param agentId
     * @return
     */
    AgentTempChatDto createTempChat(Long agentId);

    /**
     * 删除临时链接
     *
     * @param agentId
     * @param id
     */
    void deleteTempChat(Long agentId, Long id);

    /**
     * 修改临时链接
     *
     * @param agentTempChatDto
     */
    void updateTempChat(AgentTempChatDto agentTempChatDto);

    /**
     * 查询临时链接
     *
     * @param agentId
     * @return
     */
    List<AgentTempChatDto> queryTempChatList(Long agentId);

    /**
     * 根据chatKey查询临时链接
     *
     * @param chatKey
     * @return
     */
    AgentTempChatDto queryTempChatByChatKey(String chatKey);
}
