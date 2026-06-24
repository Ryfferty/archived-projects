package com.xspaceagi.agent.core.adapter.application;


import com.xspaceagi.agent.core.adapter.dto.*;
import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.adapter.dto.config.plugin.PluginDto;

import java.util.List;

public interface PluginApplicationService {


    /**
     * 新增插件
     */
    Long add(PluginAddDto pluginAddDto);

    /**
     * 更新插件
     */
    void update(PluginUpdateDto pluginUpdateDto);

    List<Arg> analysisPluginOutput(AnalysisHttpPluginOutputDto analysisHttpPluginOutputDto);

    /**
     * 删除插件
     */
    void delete(Long pluginId);

    /**
     * 复制插件
     *
     * @return
     */
    Long copyPlugin(Long userId, Long pluginId);

    Long copyPlugin(Long userId, PluginDto pluginDto, Long targetSpaceId);

    /**
     * 删除空间下的所有插件
     *
     * @param spaceId
     */
    void deleteBySpaceId(Long spaceId);

    /**
     * 获取空间下的插件列表
     */
    List<PluginDto> queryListBySpaceId(Long spaceId);

    /**
     * 根据ID获取插件列表
     *
     * @return
     */
    List<PluginDto> queryListByIds(List<Long> pluginIds);

    /**
     * 根据ID获取插件配置
     *
     * @return
     */
    PluginDto queryById(Long pluginId);

    /**
     * 获取发布状态的插件配置
     *
     * @param pluginId
     * @return
     */
    PluginDto queryPublishedPluginConfig(Long pluginId, Long spaceId);

    /**
     * 测试插件执行
     *
     * @return
     */
    PluginExecuteResultDto execute(PluginExecuteRequestDto pluginExecuteRequestDto, PluginDto pluginDto);

    /**
     * 检查空间插件权限
     *
     * @param spaceId
     * @param pluginId
     */
    void checkSpacePluginPermission(Long spaceId, Long pluginId);

    /**
     * 检查插件配置完整性
     *
     * @param pluginDto
     * @return
     */
    List<String> validatePluginConfig(PluginDto pluginDto);
}
