package com.xspaceagi.eco.market.domain.adaptor;

import com.xspaceagi.agent.core.sdk.dto.AgentInfoDto;
import com.xspaceagi.agent.core.sdk.dto.PluginEnableOrUpdateDto;
import com.xspaceagi.agent.core.sdk.dto.TemplateEnableOrUpdateDto;
import com.xspaceagi.agent.core.sdk.enums.TargetTypeEnum;
import com.xspaceagi.mcp.sdk.dto.McpDto;

import java.util.List;

public interface IEcoMarketAdaptor {

    List<AgentInfoDto> queryAgentInfoList(List<Long> agentIds);

    /**
     * 查询插件配置信息
     *
     * @param pluginId
     * @param paramJson
     * @return
     */
    String queryPluginConfig(Long pluginId, String paramJson);

    /**
     * 插件启动或更新
     *
     * @return 返回系统生成的插件ID
     */
    Long pluginEnableOrUpdate(PluginEnableOrUpdateDto pluginEnableOrUpdateDto);

    /**
     * 禁用插件
     *
     * @return
     */
    Void disablePlugin(Long pluginId);

    /**
     * 查询模板配置信息（智能体、工作流）
     *
     * @return
     */
    String queryTemplateConfig(TargetTypeEnum targetType, Long targetId);

    /**
     * 模板启动或更新
     *
     * @return 系统生成的工作流或智能体ID
     */
    Long templateEnableOrUpdate(TemplateEnableOrUpdateDto templateEnableOrUpdateDto);

    /**
     * 禁用模板
     *
     * @return
     */
    Void disableTemplate(TargetTypeEnum targetType, Long targetId);



    /**
     * 部署MCP
     * @param mcpDto
     * @return
     */
    Long deployOfficialMcp(McpDto mcpDto);

    /**
     * 停止MCP
     * @param targetId
     * @return
     */
    Void  stopOfficialMcp(Long targetId);
}
