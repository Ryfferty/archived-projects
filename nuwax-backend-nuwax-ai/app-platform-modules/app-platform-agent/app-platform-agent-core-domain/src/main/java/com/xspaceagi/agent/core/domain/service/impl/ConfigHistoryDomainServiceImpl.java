package com.xspaceagi.agent.core.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.xspaceagi.agent.core.adapter.repository.ConfigHistoryRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.ConfigHistory;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.UserTargetRelation;
import com.xspaceagi.agent.core.domain.service.ConfigHistoryDomainService;
import com.xspaceagi.agent.core.domain.service.UserTargetRelationDomainService;
import com.xspaceagi.system.sdk.service.AbstractTaskExecuteService;
import com.xspaceagi.system.sdk.service.ScheduleTaskApiService;
import com.xspaceagi.system.sdk.service.dto.ScheduleTaskDto;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.utils.RedisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("configHistoryDomainService")
public class ConfigHistoryDomainServiceImpl extends AbstractTaskExecuteService implements ConfigHistoryDomainService {

    @Resource
    private ConfigHistoryRepository configHistoryRepository;

    @Resource
    private UserTargetRelationDomainService userTargetRelationDomainService;

    @Resource
    private ScheduleTaskApiService scheduleTaskApiService;

    @Resource
    private RedisUtil redisUtil;

    @PostConstruct
    private void init() {
        try {
            scheduleTaskApiService.start(ScheduleTaskDto.builder()
                    .taskId("configHistoryDomainService")
                    .beanId("configHistoryDomainService")
                    .maxExecTimes(Long.MAX_VALUE)
                    .cron(ScheduleTaskDto.Cron.EVERY_MINUTE.getCron())
                    .params(Map.of())
                    .build());
        } catch (Exception e) {
            log.warn("start configHistoryDomainService error {}", e.getMessage());
        }
    }

    @Override
    public void addConfigHistory(ConfigHistory configHistory) {
        Assert.notNull(configHistory, "configHistory can not be null");
        Assert.notNull(configHistory.getTargetType(), "targetType can not be null");
        Assert.notNull(configHistory.getType(), "type can not be null");
        Assert.notNull(configHistory.getConfig(), "config can not be null");
        configHistory.setTenantId(RequestContext.get().getTenantId());
        // 启用 LargeObject 特性，支持写入包含大文件内容的配置历史
        redisUtil.leftPush("config_history_queue", JSON.toJSONString(configHistory, JSONWriter.Feature.LargeObject));

        ConfigHistory.Type type = configHistory.getType();
        //添加或更新用户最近编辑
        if (configHistory.getTargetType() == Published.TargetType.Agent && (type == ConfigHistory.Type.Add || type == ConfigHistory.Type.Edit
                || type == ConfigHistory.Type.AddComponent || type == ConfigHistory.Type.EditComponent
                || type == ConfigHistory.Type.DeleteComponent)) {
            UserTargetRelation userAgent = UserTargetRelation.builder().userId(configHistory.getOpUserId()).targetType(Published.TargetType.Agent).targetId(configHistory.getTargetId()).type(UserTargetRelation.OpType.Edit).build();
            userTargetRelationDomainService.addOrUpdateRecentEdit(userAgent);
        }

    }

    @Override
    public boolean execute(ScheduleTaskDto scheduleTask) {
        log.debug("execute config history task");
        Map<Published.TargetType, Map<Long, ConfigHistory>> targetMap = new HashMap<>();
        Object configStr = redisUtil.rightPop("config_history_queue");
        while (configStr != null) {
            ConfigHistory configHistory = JSON.parseObject(configStr.toString(), ConfigHistory.class);
            if (configHistory != null) {
                if (targetMap.get(configHistory.getTargetType()) == null) {
                    targetMap.put(configHistory.getTargetType(), new HashMap<>());
                }
                targetMap.get(configHistory.getTargetType()).put(configHistory.getTargetId(), configHistory);
            }
            configStr = redisUtil.rightPop("config_history_queue");
        }
        if (targetMap.size() > 0) {
            log.info("config history size: {}", targetMap.size());
            RequestContext.addTenantIgnoreEntity(ConfigHistory.class);
            try {
                for (Map.Entry<Published.TargetType, Map<Long, ConfigHistory>> entry : targetMap.entrySet()) {
                    Map<Long, ConfigHistory> configHistoryMap = entry.getValue();
                    configHistoryRepository.saveBatch(configHistoryMap.values());
                }
            } catch (Exception e) {
                log.error("save config history error", e);
            } finally {
                RequestContext.removeTenantIgnoreEntity(ConfigHistory.class);
            }
        }
        return false;
    }
}
