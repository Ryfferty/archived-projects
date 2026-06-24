package com.xspaceagi.sandbox.application.task;

import com.xspaceagi.agent.core.sdk.IConversationRpcService;
import com.xspaceagi.agent.core.sdk.dto.ConversationRpcDto;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxConfig;
import com.xspaceagi.sandbox.infra.dao.service.SandboxConfigService;
import com.xspaceagi.sandbox.infra.network.ReverseServerContainer;
import com.xspaceagi.system.sdk.service.ScheduleTaskApiService;
import com.xspaceagi.system.sdk.service.TaskExecuteService;
import com.xspaceagi.system.sdk.service.dto.ScheduleTaskDto;
import com.xspaceagi.system.spec.tenant.thread.TenantFunctions;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("userSandboxAgentAliveCheckTask")
public class UserSandboxAgentAliveCheckTask implements TaskExecuteService {

    @Resource
    private ScheduleTaskApiService scheduleTaskApiService;

    @Resource
    private SandboxConfigService sandboxConfigService;

    @Resource
    private IConversationRpcService iConversationRpcService;

    @Resource
    private ReverseServerContainer reverseServerContainer;

    @PostConstruct
    public void init() {
        scheduleTaskApiService.start(ScheduleTaskDto.builder()
                .taskId("userSandboxAgentAliveCheckTask")
                .beanId("userSandboxAgentAliveCheckTask")
                .maxExecTimes(Long.MAX_VALUE)
                .cron(ScheduleTaskDto.Cron.EVERY_5_MINUTE.getCron())
                .params(Map.of())
                .build());
    }

    @Override
    public Mono<Boolean> asyncExecute(ScheduleTaskDto scheduleTask) {
        TenantFunctions.runWithIgnoreCheck(this::execute);
        return Mono.just(false);
    }

    private void execute() {
        List<SandboxConfig> sandboxConfigs = sandboxConfigService.queryGlobalConfigs(true);
        // 获取ID列表
        List<Long> sandboxIds = sandboxConfigs.stream().map(SandboxConfig::getId).toList();
        List<ConversationRpcDto> conversationRpcDtos = iConversationRpcService.queryLatestSandboxConversationList(sandboxIds);
        log.debug("查询最新会话 {}", conversationRpcDtos);
        //获取sandboxServerIds，并去重
        List<Long> sandboxServerIds = conversationRpcDtos.stream().map(ConversationRpcDto::getSandboxServerId).distinct().toList();
        if (CollectionUtils.isEmpty(sandboxServerIds)) {
            return;
        }
        sandboxServerIds.forEach(sandboxServerId -> {
            SandboxConfig sandboxConfig = sandboxConfigService.querySandboxConfigById(sandboxServerId);
            if (sandboxConfig.getIsActive() != null && !sandboxConfig.getIsActive()) {
                log.debug("sandboxServerId {} 已停用", sandboxServerId);
                return;
            }
            if (reverseServerContainer.getUserSandboxAliveTime(sandboxConfig.getConfigKey()) == null) {
                log.debug("sandboxServerId {} 不在线", sandboxServerId);
                return;//不在线
            }
            List<ConversationRpcDto> sandboxAliveConversations = iConversationRpcService.queryLatestConversationListBySandboxId(sandboxServerId);
            log.debug("查询 {} 的最新会话 {}", sandboxServerId, sandboxAliveConversations);
            long now = System.currentTimeMillis();
            //24小时以上没有使用的会话直接停止
            sandboxAliveConversations.forEach(conversationRpcDto -> {
                if (now - conversationRpcDto.getModified().getTime() > 1000 * 60 * 60 * 24) {
                    try {
                        log.debug("停止代理 {}", conversationRpcDto);
                        iConversationRpcService.stopAgent(conversationRpcDto.getAgentId(), conversationRpcDto.getId());
                        log.debug("停止代理成功 {}", conversationRpcDto);
                    } catch (Exception e) {
                        log.warn("停止代理异常 {}, {}", conversationRpcDto, e.getMessage());
                    }
                }
            });

            sandboxAliveConversations.removeIf(conversationRpcDto -> now - conversationRpcDto.getModified().getTime() > 1000 * 60 * 60 * 24);
            if (sandboxConfig.getMaxAgentCount() == null || sandboxAliveConversations.size() <= sandboxConfig.getMaxAgentCount()) {
                return;//没有超过最大代理数
            }
            //停止超过最大代理数的代理，按照时间最旧的停止
            sandboxAliveConversations.sort(Comparator.comparing(ConversationRpcDto::getModified).reversed());
            for (int i = sandboxConfig.getMaxAgentCount(); i < sandboxAliveConversations.size(); i++) {
                try {
                    log.debug("停止超过数量的代理 {}", sandboxAliveConversations.get(i));
                    iConversationRpcService.stopAgent(sandboxAliveConversations.get(i).getAgentId(), sandboxAliveConversations.get(i).getId());
                    log.debug("停止超过数量的代理成功 {}", sandboxAliveConversations.get(i));
                } catch (Exception e) {
                    log.warn("停止超过数量的代理异常 {}, {}", sandboxAliveConversations.get(i), e.getMessage());
                }
            }
        });
    }
}
