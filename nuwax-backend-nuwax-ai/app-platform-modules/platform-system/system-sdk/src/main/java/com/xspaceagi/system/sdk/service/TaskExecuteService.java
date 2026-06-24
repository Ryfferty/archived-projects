package com.xspaceagi.system.sdk.service;

import com.xspaceagi.system.sdk.service.dto.ScheduleTaskDto;
import reactor.core.publisher.Mono;

public interface TaskExecuteService {

    Mono<Boolean> asyncExecute(ScheduleTaskDto scheduleTask);
}
