package com.xspaceagi.agent.web.ui.controller;

import com.xspaceagi.agent.core.infra.dao.mapper.*;
import com.xspaceagi.agent.web.ui.dto.ConversationStatsDto;
import com.xspaceagi.compose.sdk.service.IComposeDbTableRpcService;
import com.xspaceagi.custompage.sdk.ICustomPageRpcService;
import com.xspaceagi.knowledge.sdk.sevice.IKnowledgeConfigRpcService;
import com.xspaceagi.mcp.sdk.IMcpApiService;
import com.xspaceagi.system.application.service.SpaceApplicationService;
import com.xspaceagi.system.spec.annotation.RequireResource;
import com.xspaceagi.system.spec.dto.ReqResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.xspaceagi.system.spec.enums.ResourceEnum.SYSTEM_DASHBOARD;

@Tag(name = "平台统计接口")
@RestController
@RequestMapping("/api/system/stats")
public class PlatformStatsController {

    @Resource
    private SpaceApplicationService spaceApplicationService;

    @Resource
    private IKnowledgeConfigRpcService iKnowledgeConfigRpcService;

    @Resource
    private IComposeDbTableRpcService iComposeDbTableRpcService;

    @Resource
    private ICustomPageRpcService iCustomPageRpcService;

    @Resource
    private IMcpApiService iMcpApiService;

    // 本模块内的 Mapper，可以直接使用
    @Resource
    private AgentConfigMapper agentConfigMapper;

    @Resource
    private WorkflowConfigMapper workflowConfigMapper;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private PluginConfigMapper pluginConfigMapper;

    @Resource
    private SkillConfigMapper skillConfigMapper;

    @Resource
    private ConversationMapper conversationMapper;

    @RequireResource(SYSTEM_DASHBOARD)
    @Operation(summary = "获取平台资源总数统计")
    @RequestMapping(path = "/resources/total", method = RequestMethod.GET)
    public ReqResult<PlatformStatsDto> getTotalStats() {
        Long spaceCount = spaceApplicationService.countTotalSpaces();
        Long agentCount = agentConfigMapper.selectCount(null);
        Long workflowCount = workflowConfigMapper.selectCount(null);
        Long knowledgeCount = getKnowledgeCount();
        Long tableCount = getTableCount();
        Long mcpCount = getMcpCount();
        Long pageCount = getPageCount();
        Long modelCount = modelMapper.selectCount(null);
        Long pluginCount = pluginConfigMapper.selectCount(null);
        Long skillCount = skillConfigMapper.selectCount(null);

        PlatformStatsDto stats = PlatformStatsDto.builder()
                .spaceCount(spaceCount != null ? spaceCount : 0L)
                .agentCount(agentCount != null ? agentCount : 0L)
                .workflowCount(workflowCount != null ? workflowCount : 0L)
                .knowledgeCount(knowledgeCount)
                .tableCount(tableCount)
                .mcpCount(mcpCount)
                .pageCount(pageCount)
                .modelCount(modelCount != null ? modelCount : 0L)
                .pluginCount(pluginCount != null ? pluginCount : 0L)
                .skillCount(skillCount != null ? skillCount : 0L)
                .build();

        return ReqResult.success(stats);
    }

    /**
     * 通过 RPC 获取知识库总数
     */
    private Long getKnowledgeCount() {
        try {
            return iKnowledgeConfigRpcService.countTotalKnowledge(null);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 通过 RPC 获取数据表总数
     */
    private Long getTableCount() {
        try {
            return iComposeDbTableRpcService.countTotalTables();
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 通过 RPC 获取 MCP 总数
     */
    private Long getMcpCount() {
        try {
            return iMcpApiService.countTotalMcps();
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 通过 RPC 获取网页应用总数
     */
    private Long getPageCount() {
        try {
            return iCustomPageRpcService.countTotalPages();
        } catch (Exception e) {
            return 0L;
        }
    }

    @RequireResource(SYSTEM_DASHBOARD)
    @Operation(summary = "获取会话统计")
    @RequestMapping(path = "/conversations", method = RequestMethod.GET)
    public ReqResult<ConversationStatsDto> getConversationStats() {
        Long totalConversations = conversationMapper.countTotalConversations();
        Long todayNewConversations = conversationMapper.countTodayNewConversations();

        List<Map<String, Object>> last7DaysTrend = conversationMapper.getLast7DaysConversationTrend();
        List<Map<String, Object>> last30DaysTrend = conversationMapper.getLast30DaysConversationTrend();
        List<Map<String, Object>> monthlyTrend = conversationMapper.getMonthlyConversationTrend();

        ConversationStatsDto stats = ConversationStatsDto.builder()
                .totalConversations(totalConversations != null ? totalConversations : 0L)
                .todayNewConversations(todayNewConversations != null ? todayNewConversations : 0L)
                .last7DaysTrend(convertToTrendItems(last7DaysTrend))
                .last30DaysTrend(convertToTrendItems(last30DaysTrend))
                .monthlyTrend(convertToMonthlyTrendItems(monthlyTrend))
                .build();

        return ReqResult.success(stats);
    }

    private List<ConversationStatsDto.TrendItem> convertToTrendItems(List<Map<String, Object>> trendData) {
        return trendData.stream()
                .map(row -> ConversationStatsDto.TrendItem.builder()
                        .date(String.valueOf(row.get("date")))
                        .conversationCount(Long.valueOf(String.valueOf(row.get("conversation_count"))))
                        .build())
                .toList();
    }

    private List<ConversationStatsDto.TrendItem> convertToMonthlyTrendItems(List<Map<String, Object>> trendData) {
        return trendData.stream()
                .map(row -> ConversationStatsDto.TrendItem.builder()
                        .date(String.valueOf(row.get("month")))
                        .conversationCount(Long.valueOf(String.valueOf(row.get("conversation_count"))))
                        .build())
                .toList();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlatformStatsDto {
        private Long spaceCount;
        private Long agentCount;
        private Long workflowCount;
        private Long knowledgeCount;
        private Long tableCount;
        private Long mcpCount;
        private Long pageCount;
        private Long modelCount;
        private Long pluginCount;
        private Long skillCount;
    }
}