package com.xspaceagi.system.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.system.infra.dao.entity.UserReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserReqMapper extends BaseMapper<UserReq> {

    @Select("${sql}")
    List<Map<String, Object>> select(@Param("sql") String sql);

    @Select("SELECT COUNT(DISTINCT user_id) FROM user_req " +
            "WHERE `dt` = DATE_FORMAT(NOW(), '%Y%m%d')")
    Long countTodayUsers();

    @Select("SELECT COUNT(DISTINCT user_id) FROM user_req " +
            "WHERE `dt` >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 7 DAY), '%Y%m%d')")
    Long countLast7DaysUsers();

    @Select("SELECT COUNT(DISTINCT user_id) FROM user_req " +
            "WHERE `dt` >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 30 DAY), '%Y%m%d')")
    Long countLast30DaysUsers();

    @Select("SELECT dt as date, COUNT(DISTINCT user_id) as user_count " +
            "FROM user_req " +
            "WHERE `dt` >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 7 DAY), '%Y%m%d')" +
            "GROUP BY dt " +
            "ORDER BY dt")
    List<Map<String, Object>> getLast7DaysTrend();

    /**
     * 插入或更新用户每日请求统计
     * 不存在则插入，存在则请求次数+1
     *
     * @param tenantId 商户ID
     * @param userId   用户ID
     * @param dt       日期 YYYYMMDD
     * @return 影响行数
     */
    int insertOrUpdate(@Param("tenantId") Long tenantId,
                       @Param("userId") Long userId,
                       @Param("dt") String dt);

    /**
     * 插入或更新用户每日请求统计（使用当前日期）
     * 不存在则插入，存在则请求次数+1
     *
     * @param tenantId 商户ID
     * @param userId   用户ID
     * @return 影响行数
     */
    int insertOrUpdateToday(@Param("tenantId") Long tenantId,
                            @Param("userId") Long userId);
}
