package com.xspaceagi.agent.core.adapter.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.PublishedStatistics;

public interface PublishedStatisticsRepository extends IService<PublishedStatistics> {

    void incCount(Published.TargetType targetType, Long targetId, String key, Long inc);
}
