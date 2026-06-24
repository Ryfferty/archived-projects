package com.xspaceagi.agent.core.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xspaceagi.agent.core.adapter.repository.entity.SkillConfig;

import java.util.List;

public interface SkillDomainService {

    /**
     * 添加技能
     */
    Long add(SkillConfig skillConfig);

    /**
     * 删除技能
     */
    void delete(Long skillId);

    /**
     * 更新技能
     */
    void update(SkillConfig skillConfig);

    /**
     * 根据ID查询技能
     */
    SkillConfig queryById(Long skillId, boolean loadFiles);

    /**
     * 根据查询条件查询技能列表
     */
    List<SkillConfig> list(LambdaQueryWrapper<SkillConfig> queryWrapper);

    /**
     * 根据ID列表查询技能列表
     */
    List<SkillConfig> listByIds(List<Long> skillIds);
}
