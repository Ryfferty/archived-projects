package com.xspaceagi.knowledge.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author soddy
 * @description 针对表【knowledge_task(知识库-定时任务)】的数据库操作Mapper
 * @createDate 2025-02-21 11:02:01
 * @Entity com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeTask
 */
public interface KnowledgeTaskMapper extends BaseMapper<KnowledgeTask> {


    /**
     * 查询最近几天,需要重试的任务,最近5分钟的数据,不进行重试,防止正在运行
     *
     * @param days 最近xx天的数据,进行重试
     * @return
     */
    List<KnowledgeTask> queryListForRetryByDays(@Param("days") Integer days);

    /**
     * 查询最近成功的数据,进行归档
     *
     * @param days 对xx天以前的成功数据,进行归档
     * @return
     */
    List<KnowledgeTask> queryListForArchiveByDaysAndSuccess(@Param("days") Integer days);


    /**
     * 重试次数加1
     *
     * @param id id
     */
    void incrementRetryCount(@Param("id") Long id);

}




