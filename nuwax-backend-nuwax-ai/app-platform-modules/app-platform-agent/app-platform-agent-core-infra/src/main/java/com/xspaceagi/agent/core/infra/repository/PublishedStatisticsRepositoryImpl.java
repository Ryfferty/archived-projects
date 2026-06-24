package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.PublishedStatisticsRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.PublishedStatistics;
import com.xspaceagi.agent.core.infra.dao.mapper.PublishedStatisticsMapper;
import org.springframework.stereotype.Service;

@Service
public class PublishedStatisticsRepositoryImpl extends ServiceImpl<PublishedStatisticsMapper, PublishedStatistics> implements PublishedStatisticsRepository {
    @Override
    public void incCount(Published.TargetType targetType, Long targetId, String key, Long inc) {
        if (getBaseMapper().incCount(targetType, targetId, key, inc) == 0) {
            PublishedStatistics publishedStatistics = new PublishedStatistics();
            publishedStatistics.setTargetType(targetType);
            publishedStatistics.setTargetId(targetId);
            publishedStatistics.setName(key);
            publishedStatistics.setValue(inc);
            try {
                save(publishedStatistics);
            } catch (Exception e) {
                //在inc为0时，save会报错，所以这里try catch一下
            }
        }
    }
}
