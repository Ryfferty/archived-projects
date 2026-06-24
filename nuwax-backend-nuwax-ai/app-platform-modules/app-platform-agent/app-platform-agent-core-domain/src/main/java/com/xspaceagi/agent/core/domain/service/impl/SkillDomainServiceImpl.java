package com.xspaceagi.agent.core.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xspaceagi.agent.core.adapter.repository.SkillConfigRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.SkillConfig;
import com.xspaceagi.agent.core.domain.service.PublishDomainService;
import com.xspaceagi.agent.core.domain.service.SkillDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillDomainServiceImpl implements SkillDomainService {

    @Resource
    private SkillConfigRepository skillConfigRepository;
    @Resource
    private PublishDomainService publishDomainService;

    @Override
    public Long add(SkillConfig skillConfig) {
        skillConfigRepository.save(skillConfig);
        return skillConfig.getId();
    }

    @Override
    public void delete(Long skillId) {
        skillConfigRepository.removeById(skillId);
        publishDomainService.deleteByTargetId(Published.TargetType.Skill, skillId);
        publishDomainService.deletePublishedApply(Published.TargetType.Skill, skillId);
    }

    @Override
    public void update(SkillConfig skillConfig) {
        skillConfigRepository.updateById(skillConfig);
    }

    @Override
    public SkillConfig queryById(Long skillId, boolean loadFiles) {
        if (!loadFiles) {
            // 如果 loadFiles=false，不查询 files 字段，避免大字段反序列化开销
            LambdaQueryWrapper<SkillConfig> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SkillConfig::getId, skillId)
                    .select(SkillConfig.class, info -> !"files".equals(info.getColumn()));
            return skillConfigRepository.getOne(queryWrapper);
        }
        return skillConfigRepository.getById(skillId);
    }

    @Override
    public List<SkillConfig> list(LambdaQueryWrapper<SkillConfig> queryWrapper) {
        return skillConfigRepository.list(queryWrapper);
    }

    @Override
    public List<SkillConfig> listByIds(List<Long> skillIds) {
        return skillConfigRepository.listByIds(skillIds);
    }
}
