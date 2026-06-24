package com.xspaceagi.system.domain.service;


import com.xspaceagi.system.infra.dao.entity.ScheduleTask;

public interface ScheduleTaskDomainService {

    Long start(ScheduleTask scheduleTask);

    void update(ScheduleTask scheduleTask);

    void complete(String taskId);

    void cancel(String taskId);
}
