package com.xspaceagi.agent.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.agent.core.adapter.repository.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ConversationMapper extends BaseMapper<Conversation> {

    /**
     * 统计会话总数（包含 topic_updated=1 条件）
     */
    @Select("SELECT COUNT(*) FROM conversation WHERE topic_updated = 1")
    Long countTotalConversations();

    /**
     * 统计今日新增会话数（包含 topic_updated=1 条件）
     */
    @Select("SELECT COUNT(*) FROM conversation WHERE topic_updated = 1 AND created >= CURDATE() AND created < CURDATE() + INTERVAL 1 DAY")
    Long countTodayNewConversations();

    /**
     * 查询最近7天新增会话趋势（包含 topic_updated=1 条件）
     */
    @Select("SELECT DATE(created) as date, COUNT(*) as conversation_count " +
            "FROM conversation WHERE topic_updated = 1 AND created >= CURDATE() - INTERVAL 7 DAY " +
            "GROUP BY DATE(created) ORDER BY date")
    List<Map<String, Object>> getLast7DaysConversationTrend();

    /**
     * 查询最近30天新增会话趋势（包含 topic_updated=1 条件）
     */
    @Select("SELECT DATE(created) as date, COUNT(*) as conversation_count " +
            "FROM conversation WHERE topic_updated = 1 AND created >= CURDATE() - INTERVAL 30 DAY " +
            "GROUP BY DATE(created) ORDER BY date")
    List<Map<String, Object>> getLast30DaysConversationTrend();

    /**
     * 按月统计新增会话趋势（包含 topic_updated=1 条件）
     */
    @Select("SELECT DATE_FORMAT(created, '%Y-%m') as month, COUNT(*) as conversation_count " +
            "FROM conversation WHERE topic_updated = 1 " +
            "GROUP BY DATE_FORMAT(created, '%Y-%m') ORDER BY month DESC LIMIT 12")
    List<Map<String, Object>> getMonthlyConversationTrend();

}
