package com.xspaceagi.agent.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.PublishedStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PublishedStatisticsMapper extends BaseMapper<PublishedStatistics> {

    @Update("UPDATE published_statistics SET value = value + #{inc} WHERE target_type=#{targetType} AND target_id=#{targetId} AND name=#{key}")
    int incCount(@Param("targetType") Published.TargetType targetType, @Param("targetId") Long targetId, @Param("key") String key, @Param("inc") Long inc);
}
