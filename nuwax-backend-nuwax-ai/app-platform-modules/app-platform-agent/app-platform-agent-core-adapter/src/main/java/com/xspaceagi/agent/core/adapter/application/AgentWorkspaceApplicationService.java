package com.xspaceagi.agent.core.adapter.application;

import com.xspaceagi.agent.core.adapter.dto.AddSkillsToWorkspaceDto;
import com.xspaceagi.agent.core.adapter.dto.CreateWorkspaceDto;

public interface AgentWorkspaceApplicationService {


    /**
     * 创建/重置工作空间（不会重复创建，只重置skill/subagent，不会清除用户文件）
     * 如果传了技能id列表，会同时推送技能文件
     * 如果传了subagent列表，会同时推送subagent文件
     */
    void createWorkspace(CreateWorkspaceDto createWorkspaceDto);

    /**
     * 动态添加技能到工作空间
     * 支持传多个技能 id，动态增加的技能在重置工作空间时不会被清除
     */
    void addSkillsToWorkspace(AddSkillsToWorkspaceDto addSkillsToWorkspaceDto);

}