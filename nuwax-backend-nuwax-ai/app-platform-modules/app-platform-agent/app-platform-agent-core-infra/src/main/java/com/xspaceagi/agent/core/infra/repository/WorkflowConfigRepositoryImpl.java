package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.WorkflowConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.WorkflowConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.WorkflowConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class WorkflowConfigRepositoryImpl extends ServiceImpl<WorkflowConfigMapper, WorkflowConfig> implements WorkflowConfigRepository {
}
