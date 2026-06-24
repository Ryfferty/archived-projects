package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.AgentTempChatRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.AgentTempChat;
import com.xspaceagi.agent.core.infra.dao.mapper.AgentTempChatMapper;
import org.springframework.stereotype.Service;

@Service
public class AgentTempChatRepositoryImpl extends ServiceImpl<AgentTempChatMapper, AgentTempChat> implements AgentTempChatRepository {
}
