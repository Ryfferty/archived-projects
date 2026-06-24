package com.xspaceagi.agent.core.application.service;

import com.xspaceagi.agent.core.adapter.application.PublishApplicationService;
import com.xspaceagi.system.sdk.service.AbstractTaskExecuteService;
import com.xspaceagi.system.sdk.service.ScheduleTaskApiService;
import com.xspaceagi.system.sdk.service.dto.ScheduleTaskDto;
import com.xspaceagi.system.spec.utils.RedisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service("spaceDeleteTaskService")
public class SpaceDeleteTaskServiceImpl extends AbstractTaskExecuteService {

    @Resource
    private ScheduleTaskApiService scheduleTaskApiService;

    @Resource
    private PublishApplicationService publishApplicationService;

    @Resource
    private RedisUtil redisUtil;

    @PostConstruct
    private void init() {
        scheduleTaskApiService.start(ScheduleTaskDto.builder()
                .taskId("spaceDeleteTaskService")
                .beanId("spaceDeleteTaskService")
                .maxExecTimes(Long.MAX_VALUE)
                .cron(ScheduleTaskDto.Cron.EVERY_10_SECOND.getCron())
                .params(Map.of())
                .build());
    }

    @Override
    public boolean execute(ScheduleTaskDto scheduleTask) {
        Set<Long> spaceIds = new HashSet<>();
        Object id = redisUtil.rightPop("delete_space_queue");
        while (id != null) {
            Long spaceId = Long.valueOf(id.toString());
            if (!spaceIds.contains(spaceId)) {
                try {
                    publishApplicationService.deleteBySpaceId(spaceId);
                    spaceIds.add(spaceId);
                } catch (Exception e) {
                    //ignore
                    log.error("删除空间关联信息失败 {}", spaceId, e);
                }
            }
            id = redisUtil.rightPop("delete_space_queue");
        }
        return false;
    }
}
