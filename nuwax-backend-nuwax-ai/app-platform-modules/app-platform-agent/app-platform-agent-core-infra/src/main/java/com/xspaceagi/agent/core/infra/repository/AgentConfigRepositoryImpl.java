package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.infra.dao.mapper.AgentConfigMapper;
import com.xspaceagi.agent.core.adapter.repository.AgentConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.AgentConfig;
import org.springframework.stereotype.Service;

@Service
public class AgentConfigRepositoryImpl extends ServiceImpl<AgentConfigMapper, AgentConfig> implements AgentConfigRepository {
}
