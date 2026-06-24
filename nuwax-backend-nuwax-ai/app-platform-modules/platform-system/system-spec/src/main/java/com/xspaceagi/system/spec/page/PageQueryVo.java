package com.xspaceagi.system.spec.page;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分页查询VO
 * </p>
 */
@Getter
@Setter
@Schema(description = "分页查询VO")
public class PageQueryVo<T> {

    @Schema(description = "分页查询过滤条件", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private T queryFilter;

    @Schema(description = "当前页", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "1", example = "1")
    private Long current = 1L;

    @Schema(description = "分页pageSize", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "10", example = "10")
    private Long pageSize = 10L;


    /**
     * 排序字段信息,可空,一般没有默认为创建时间排序
     */
    @Schema(description = "排序字段信息,可空,一般没有默认为创建时间排序", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<OrderItem> orders = new ArrayList<>();


    /**
     * 列的筛选条件,可空
     */
    @Schema(description = "列的筛选条件,可空", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ComparisonExpression> filters = new ArrayList<>();

    /**
     * 表格的列信息,可空
     */
    @Schema(description = "表格的列信息,可空", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SuperTableColumn> columns = new ArrayList<>();


}
