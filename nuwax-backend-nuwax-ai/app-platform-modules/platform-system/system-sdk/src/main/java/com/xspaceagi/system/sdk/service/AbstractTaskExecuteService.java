package com.xspaceagi.system.sdk.service;

import com.xspaceagi.system.sdk.service.dto.ScheduleTaskDto;
import reactor.core.publisher.Mono;

public abstract class AbstractTaskExecuteService implements TaskExecuteService {


    @Override
    public Mono<Boolean> asyncExecute(ScheduleTaskDto scheduleTask) {
        return Mono.fromCallable(() -> execute(scheduleTask));
    }

    protected abstract boolean execute(ScheduleTaskDto scheduleTaskDto);
}
