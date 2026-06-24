package com.xspaceagi.system.domain.model;

import com.xspaceagi.system.spec.enums.BindTypeEnum;
import com.xspaceagi.system.spec.enums.OpenTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单节点模型
 */
@Data
public class MenuNode implements Serializable {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 子菜单绑定类型 0:未绑定 1:全部绑定 2:部分绑定
     * @see BindTypeEnum
     */
    private Integer menuBindType;

    /**
     * 资源树（包含每个节点的绑定类型）
     */
    private List<ResourceNode> resourceTree;

    /**
     * 资源列表（包含每个节点的绑定类型）
     */
    private List<ResourceNode> resourceNodes;

    /**
     * 子菜单列表
     */
    private List<MenuNode> children;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 菜单名称
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
     * 父级ID
     */
    private Long parentId;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 打开方式
     * @see OpenTypeEnum
     */
    private Integer openType;

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

    public List<ResourceNode> getFlattenResourceList() {
        return flattenResourceTree(resourceTree);
    }


    /**
     * 资源树扁平化处理
     */
    public List<ResourceNode> flattenResourceTree(List<ResourceNode> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>();
        }

        List<ResourceNode> result = new ArrayList<>();
        for (ResourceNode node : nodes) {
            if (node.getId() != null) {
                // 创建新节点，不包含children
                ResourceNode flatNode = new ResourceNode();
                flatNode.setId(node.getId());
                flatNode.setResourceBindType(node.getResourceBindType());
                result.add(flatNode);
            }
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                result.addAll(flattenResourceTree(node.getChildren()));
            }
        }
        return result;
    }


}

