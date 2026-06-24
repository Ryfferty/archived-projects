package com.xspaceagi.system.infra.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.List;
import java.util.Map;

/**
 * 前端页面对象,查询接口
 *
 * @author soddy
 */
public interface IQueryViewService<T> {


    /**
     * 查询分页列表
     * @param queryMap 查询条件
     * @param orderColumns 排序字段
     * @param startIndex 起始
     * @param pageSize 页大小
     * @return 列表数据
     */
    List<T> pageQuery(Map<String, Object> queryMap, List<OrderItem> orderColumns, Long startIndex, Long pageSize);

    /**
     * 查询总数
     * @param queryMap 查询条件
     * @return 总数
     */
    Long queryTotal(Map<String, Object> queryMap);

}
