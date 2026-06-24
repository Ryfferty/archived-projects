package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.infra.dao.mapper.AgentNodeMapper;
import com.xspaceagi.agent.core.adapter.repository.AgentNodeRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.AgentNode;
import org.springframework.stereotype.Service;

@Service
public class AgentNodeRepositoryImpl extends ServiceImpl<AgentNodeMapper, AgentNode> implements AgentNodeRepository {
}
