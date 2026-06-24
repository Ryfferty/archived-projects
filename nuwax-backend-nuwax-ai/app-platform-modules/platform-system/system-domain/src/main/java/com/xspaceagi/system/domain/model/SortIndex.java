package com.xspaceagi.system.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 顺序调整项
 */
@Data
public class SortIndex implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 父级ID，0表示根节点，null表示不修改（无层级则忽略）
     */
    private Long parentId;

    /**
     * 排序索引，null表示不修改
     */
    private Integer sortIndex;
}
