package com.xspaceagi.system.infra.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.xspaceagi.system.spec.page.PageQueryParamVo;
import com.xspaceagi.system.spec.page.SuperPage;
import com.xspaceagi.system.spec.page.SuperTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description 列表查询
 */
@Slf4j
@Service
public class QueryVoListDelegateService {

    /**
     * 查询列表，判断是否需要查明细，如需要，先查明细返回单号，再查主表
     *
     * @param queryViewService 各自业务查询服务
     * @param pageQueryParamVo 查询筛选条件
     * @param columnList       需要展示的字段名称
     * @return 列表
     */
    public <T> SuperPage<T> queryVoList(IQueryViewService<T> queryViewService, PageQueryParamVo pageQueryParamVo,
                                        List<SuperTableColumn> columnList) {
        Long pageNo = pageQueryParamVo.getPageNo();
        Long pageSize = pageQueryParamVo.getPageSize();
        Long startIndex = pageQueryParamVo.getStartIndex();

        //处理排序字段，驼峰，转下划线，方便mapper使用
        List<OrderItem> orderItemList = getOrderItems(pageQueryParamVo);

        Map<String, Object> queryParamMap = buildQueryParamMap(queryViewService, pageQueryParamVo);

        List<T> voList = queryParamMap == null ? Lists.newArrayList()
                : queryViewService.pageQuery(queryParamMap, orderItemList, startIndex, pageSize);
        long totalCount = queryParamMap == null ? 0 : queryViewService.queryTotal(queryParamMap);

        SuperPage<T> superPage = new SuperPage<>(pageNo, pageSize, totalCount);
        superPage.setRecords(voList);
        superPage.setColumns(columnList);

        return superPage;
    }

    /**
     * 查询列表，判断是否需要查明细，如需要，先查明细返回单号，再查主表
     *
     * @param queryViewService 各自业务查询服务
     * @param pageQueryParamVo 查询筛选条件
     * @return 列表
     */
    public <T> List<T> queryVoList(IQueryViewService<T> queryViewService, PageQueryParamVo pageQueryParamVo) {
        //处理排序字段，驼峰，转下划线，方便mapper使用
        List<OrderItem> orderItemList = getOrderItems(pageQueryParamVo);

        Map<String, Object> queryParamMap = buildQueryParamMap(queryViewService, pageQueryParamVo);

        return queryParamMap == null ? Lists.newArrayList()
                : queryViewService.pageQuery(queryParamMap, orderItemList, pageQueryParamVo.getStartIndex(),
                pageQueryParamVo.getPageSize());
    }

    /**
     * 统计总数量
     *
     * @param queryViewService 各自业务查询服务
     * @param pageQueryParamVo 查询筛选条件
     * @param <T>
     * @return
     */
    public <T> long count(IQueryViewService<T> queryViewService, PageQueryParamVo pageQueryParamVo) {
        Map<String, Object> queryParamMap = buildQueryParamMap(queryViewService, pageQueryParamVo);
        log.info("查询总条数的参数 queryCount queryParamMap：{}", JSON.toJSONString(queryParamMap));
        return queryParamMap == null ? 0 : queryViewService.queryTotal(queryParamMap);
    }

    private <T> Map<String, Object> buildQueryParamMap(IQueryViewService<T> queryViewService,
                                                       PageQueryParamVo pageQueryParamVo) {

        Map<String, Object> queryMap = pageQueryParamVo.convertQueryFilterParamMap();
        return queryMap;
    }

    private List<OrderItem> getOrderItems(PageQueryParamVo pageQueryParamVo) {
        List<OrderItem> orderItemList = pageQueryParamVo.getOrderItemList();
        if (Objects.nonNull(orderItemList)) {
            for (OrderItem orderItem : orderItemList) {
                //下划线写法的字段名称
                String underScoreField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderItem.getColumn());
                orderItem.setColumn(underScoreField);
            }
        } else {
            orderItemList = new ArrayList<>(0);
        }
        return orderItemList;
    }


    /**
     * 非分页查询,自定义查询
     * @param pageQueryParamVo 查询参数
     * @return 第一个参数是查询条件,第二个参数是排序字段
     */
    public Tuple2<Map<String,Object>,List<OrderItem>> getQueryParamMapAndOrderItemList(PageQueryParamVo pageQueryParamVo){
        List<OrderItem> orderItemList = pageQueryParamVo.getOrderItemList();
        if (Objects.nonNull(orderItemList)) {
            for (OrderItem orderItem : orderItemList) {
                //下划线写法的字段名称
                String underScoreField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderItem.getColumn());
                orderItem.setColumn(underScoreField);
            }
        } else {
            orderItemList = new ArrayList<>(0);
        }
        return Tuples.of(pageQueryParamVo.convertQueryFilterParamMap(),orderItemList);
    }

}
