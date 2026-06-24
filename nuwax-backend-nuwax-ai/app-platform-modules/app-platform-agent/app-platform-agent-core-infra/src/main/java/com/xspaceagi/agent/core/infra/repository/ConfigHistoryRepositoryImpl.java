package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.ConfigHistoryRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.ConfigHistory;
import com.xspaceagi.agent.core.infra.dao.mapper.ConfigHistoryMapper;
import org.springframework.stereotype.Service;

@Service
public class ConfigHistoryRepositoryImpl extends ServiceImpl<ConfigHistoryMapper, ConfigHistory> implements ConfigHistoryRepository {
}
