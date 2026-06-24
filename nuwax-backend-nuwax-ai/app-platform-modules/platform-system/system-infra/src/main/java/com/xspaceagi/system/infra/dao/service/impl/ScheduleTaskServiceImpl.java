package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.ScheduleTask;
import com.xspaceagi.system.infra.dao.mapper.ScheduleTaskMapper;
import com.xspaceagi.system.infra.dao.service.ScheduleTaskService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskServiceImpl extends ServiceImpl<ScheduleTaskMapper, ScheduleTask> implements ScheduleTaskService {
}
