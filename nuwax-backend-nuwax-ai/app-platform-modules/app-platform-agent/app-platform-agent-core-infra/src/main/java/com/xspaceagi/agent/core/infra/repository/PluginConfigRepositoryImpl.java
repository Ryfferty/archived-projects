package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.PluginConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.PluginConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.PluginConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class PluginConfigRepositoryImpl extends ServiceImpl<PluginConfigMapper, PluginConfig> implements PluginConfigRepository {
}
