package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.WorkflowNodeConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.WorkflowNodeConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.WorkflowNodeConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class WorkflowNodeConfigRepositoryImpl extends ServiceImpl<WorkflowNodeConfigMapper, WorkflowNodeConfig> implements WorkflowNodeConfigRepository {
}
