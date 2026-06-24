package com.xspaceagi.system.spec.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 前端list交互规范结构
 */
@Setter
@Getter
@NoArgsConstructor
public class SuperPage<T> extends Page<T> implements Serializable {


    /**
     * 表格的列定义
     */
    private List<SuperTableColumn> columns = new ArrayList<>();

    /**
     * @param current 当前页
     * @param size    每页显示条数
     */
    public SuperPage(long current, long size) {
        super(current, size);
    }

    /**
     * @param current 当前页
     * @param size    每页显示条数
     * @param orders  排序字段
     */
    public SuperPage(long current, long size, List<OrderItem> orders) {
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
    public SuperPage(long pageNo, long pageSize, long total) {
        super(pageNo, pageSize, total);
    }

    /**
     * @param pageNo   当前页
     * @param pageSize 页大小
     * @param total    总条数
     * @param records  数据
     */
    public SuperPage(long pageNo, long pageSize, long total, List<T> records) {
        super(pageNo, pageSize, total);
        this.setRecords(records);
    }


    /**
     * 替换 IPage 的对象类型
     */
    public static <O> SuperPage<O> build(IPage<?> in, List<O> records) {
        return new SuperPage<>(in.getCurrent(), in.getSize(), in.getTotal(), records);
    }


}
