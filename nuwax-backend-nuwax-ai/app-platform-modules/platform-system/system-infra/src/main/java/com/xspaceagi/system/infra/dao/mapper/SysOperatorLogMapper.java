package com.xspaceagi.system.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.system.infra.dao.entity.SysOperatorLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 操作日志(SysOperatorLog)数据库访问层
 *
 * @author p1
 * @since 2024-11-01 11:16:02
 */
@Mapper
public interface SysOperatorLogMapper extends BaseMapper<SysOperatorLog> {


    /**
     * 总条数查询
     *
     * @param queryMap 筛选条件
     * @return 总条数
     */
    Long queryTotal(@Param("queryMap") Map<String, Object> queryMap);

    /**
     * 列表查询
     *
     * @param queryMap     筛选条件
     * @param orderColumns 排序
     * @param startIndex   索引开始位置
     * @param pageSize     业务大小
     * @return 列表
     */
    List<SysOperatorLog> queryList(@Param("queryMap") Map<String, Object> queryMap,
                                   @Param("orderColumns") List<OrderItem> orderColumns,
                                   @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);


}

