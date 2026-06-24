package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.AgentComponentConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.AgentComponentConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.AgentComponentConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class AgentComponentConfigRepositoryImpl extends ServiceImpl<AgentComponentConfigMapper, AgentComponentConfig> implements AgentComponentConfigRepository {
}
