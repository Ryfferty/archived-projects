package com.xspaceagi.compose.sdk;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.compose.sdk.vo.data.FrontColumnDefineVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 前端list交互规范结构
 */
@Setter
@Getter
@NoArgsConstructor
public class DorisDataPage<T> extends Page<T> {

    @Schema(description = "excel抬头字段定义")
    private List<FrontColumnDefineVo> columnDefines;

    /**
     * @param current 当前页
     * @param size    每页显示条数
     */
    public DorisDataPage(long current, long size) {
        super(current, size);
    }

    /**
     * @param current 当前页
     * @param size    每页显示条数
     * @param orders  排序字段
     */
    public DorisDataPage(long current, long size, List<OrderItem> orders) {
        super(current, size);
        if (!CollectionUtils.isEmpty(orders)) {
            this.addOrder(orders);
        }
    }

    /**
     * @param pageNo   当前页
     * @param pageSize 页大小
     * @param total    总条数
     */
    public DorisDataPage(long pageNo, long pageSize, long total) {
        super(pageNo, pageSize, total);
    }

    /**
     * @param pageNo   当前页
     * @param pageSize 页大小
     * @param total    总条数
     * @param records  数据
     */
    public DorisDataPage(long pageNo, long pageSize, long total, List<T> records) {
        super(pageNo, pageSize, total);
        this.setRecords(records);
    }

    /**
     * 替换 IPage 的对象类型
     */
    public static <O> DorisDataPage<O> build(DorisDataPage<?> in, List<O> records) {
        return new DorisDataPage<>(in.getCurrent(), in.getSize(), in.getTotal(), records);
    }
}
