package com.xspaceagi.system.spec.page;

import java.util.Objects;

/**
 * 给页码,页大小,计算开始索引,结束索引,用于mysql查询limit使用
 */
public class PageUtils {

    /**
     * 获取开始索引
     * 
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 开始索引
     */
    public static long getStartIndex(long pageNum, long pageSize) {
        if (Objects.isNull(pageNum) || pageNum < 1) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize) || pageSize < 1) {
            pageSize = 100;
        }
        return (pageNum - 1) * pageSize;
    }

    /**
     * 获取结束索引
     * 
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 结束索引
     */
    public static long getEndIndex(long pageNum, long pageSize) {
        if (Objects.isNull(pageNum) || pageNum < 1) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize) || pageSize < 1) {
            pageSize = 100;
        }
        return (pageNum - 1) * pageSize + pageSize;
    }

    /**
     * 根据总条数,和页大小,获取 页数 pages
     * 
     * @param total    总条数
     * @param pageSize 页大小
     * @return 页数
     */
    public static int getPages(long total, long pageSize) {
        if (Objects.isNull(total) || total < 1) {
            return 1;
        }
        if (Objects.isNull(pageSize) || pageSize < 1) {
            pageSize = 100;
        }
        return (int) Math.ceil((double) total / pageSize);
    }

}
