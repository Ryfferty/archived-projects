package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.SkillConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.SkillConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.SkillConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class SkillConfigRepositoryImpl extends ServiceImpl<SkillConfigMapper, SkillConfig> implements SkillConfigRepository {
}
