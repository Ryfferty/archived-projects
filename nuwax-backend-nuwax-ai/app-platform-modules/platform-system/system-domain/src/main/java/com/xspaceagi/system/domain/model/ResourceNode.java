package com.xspaceagi.system.domain.model;

import com.xspaceagi.system.spec.enums.BindTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资源树节点
 */
@Data
public class ResourceNode implements Serializable {

    /**
     * 资源ID
     */
    private Long id;

    /**
     * 子资源绑定类型 0:未绑定 1:全部绑定 2:部分绑定
     * @see BindTypeEnum
     */
    private Integer resourceBindType;

    /**
     * 子资源列表
     */
    private List<ResourceNode> children;

    /**
     * 资源编码
     */
    private String code;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 来源
     */
    private Integer source;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sortIndex;

    /**
     * 状态 1:启用 0:禁用
     */
    private Integer status;
}
