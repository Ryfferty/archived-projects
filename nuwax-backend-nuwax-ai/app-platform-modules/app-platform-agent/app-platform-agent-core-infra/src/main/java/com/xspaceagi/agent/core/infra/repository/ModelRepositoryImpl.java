package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.infra.dao.mapper.ModelMapper;
import com.xspaceagi.agent.core.adapter.repository.ModelConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.ModelConfig;
import org.springframework.stereotype.Service;

@Service
public class ModelRepositoryImpl extends ServiceImpl<ModelMapper, ModelConfig> implements ModelConfigRepository {
}
