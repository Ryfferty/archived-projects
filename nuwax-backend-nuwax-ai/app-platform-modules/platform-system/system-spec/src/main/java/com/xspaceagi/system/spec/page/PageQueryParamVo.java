package com.xspaceagi.system.spec.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.system.spec.utils.Pojo2MapUtil;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列表查询参数
 *
 * @author soddy
 */
@Data
@Slf4j
public class PageQueryParamVo implements Serializable {

    /**
     * 租户id,显示传递
     */
    private Long tenantId;
    /**
     * 是否查询明细表
     */
    private boolean queryDetailFlag = false;
    /**
     * 排序字段
     */
    private List<OrderItem> orderItemList;

    /**
     * 列筛选字段条件,不是查询条件
     */
    private List<ComparisonExpression> comparisonExpressionList;


    /**
     * 页码,页大小,位置偏移定义
     */
    private PageQueryDto pageQueryDto;

    /**
     * 抬头筛选条件
     */
    private Map<String, Object> queryMap = new HashMap<>(0);

    public PageQueryParamVo(long pageNo, long pageSize) {
        this.pageQueryDto = new PageQueryDto(pageNo, pageSize);
    }

    /**
     * request请求条件,转换为后端查询对象map
     * <p>和前端对象隔离开,参数(名称)等字段定义互不影响</p>
     *
     * @param superPage         公共属性
     */
    public <T> PageQueryParamVo(PageQueryVo<T> superPage) {
        PageQueryParamVo pageQueryParamVo = new PageQueryParamVo(superPage.getCurrent(), superPage.getPageSize());
        pageQueryParamVo.setComparisonExpressionList(superPage.getFilters());
        pageQueryParamVo.setOrderItemList(superPage.getOrders());

        Map<String, Object> queryMap = Pojo2MapUtil.object2Map(superPage.getQueryFilter());
        pageQueryParamVo.setQueryMap(queryMap);

        this.pageQueryDto = new PageQueryDto(superPage.getCurrent(), superPage.getPageSize());
        this.comparisonExpressionList = superPage.getFilters();
        this.orderItemList = superPage.getOrders();
        this.queryMap = queryMap;

    }

    public Long getPageNo() {
        return this.pageQueryDto.pageNo;
    }

    public Long getPageSize() {
        return this.pageQueryDto.pageSize;
    }

    public Long getStartIndex() {
        return this.pageQueryDto.startIndex;
    }

    /**
     * 转换为查询条件mapper
     * <p>不使用operator来动态控制,后端</p>
     *
     * @return 查询条件
     */
    public Map<String, Object> convertQueryFilterParamMap() {
        Map<String, Object> queryMap = new HashMap<>(8);
        if (null == comparisonExpressionList || comparisonExpressionList.isEmpty()) {
            if (log.isDebugEnabled()) {
                log.debug("列筛选条件为空,继续组装抬头查询条件");
            }
        } else {
            //列筛选条件
            queryMap = new HashMap<>(this.comparisonExpressionList.size());
            for (ComparisonExpression item : this.comparisonExpressionList) {
                queryMap.put(item.getColumn(), item.getValue());
            }
        }

        //抬头查询条件,追加到map里
        queryMap.putAll(this.queryMap);
        return queryMap;
    }

    @Getter
    @Setter
    public static class PageQueryDto {

        /**
         * 当前页码
         */
        private final Long pageNo;

        /**
         * 页大小
         */
        private Long pageSize;

        /**
         * 偏移位置
         */
        private Long startIndex;

        public PageQueryDto(long pageNo, long pageSize) {
            if (pageNo <= 0) {
                this.pageNo = 1L;
            } else {
                this.pageNo = pageNo;
            }
            this.pageSize = pageSize;

            this.startIndex = (this.pageNo - 1) * this.pageSize;
        }

    }
}
