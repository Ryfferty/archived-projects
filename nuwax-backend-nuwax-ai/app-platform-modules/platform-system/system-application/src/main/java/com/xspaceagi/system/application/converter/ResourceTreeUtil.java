package com.xspaceagi.system.application.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.xspaceagi.system.infra.dao.entity.SysResource;
import com.xspaceagi.system.spec.enums.BindTypeEnum;
import com.xspaceagi.system.application.dto.permission.ResourceNodeDto;

/**
 * 资源树工具类
 */
public class ResourceTreeUtil {

    /**
     * 构建资源树
     */
    public static List<ResourceNodeDto> buildResourceTree(List<SysResource> resourceList) {
        return buildResourceTree(resourceList, Map.of());
    }

    /**
     * 构建资源树
     * 
     * @param resourceList 资源列表
     * @param resourceBindTypeMap 资源ID到绑定类型的映射（key: resourceId, value: resourceBindType）
     * @return 资源树形结构
     */
    public static List<ResourceNodeDto> buildResourceTree(List<SysResource> resourceList, Map<Long, Integer> resourceBindTypeMap) {
        if (CollectionUtils.isEmpty(resourceList)) {
            return new ArrayList<>();
        }
        // 转换为 ResourceNodeDto
        List<ResourceNodeDto> nodeList = resourceList.stream().map(resource -> {
            ResourceNodeDto node = convertResourceToNode(resource);

            Integer resourceBindType = resourceBindTypeMap.containsKey(resource.getId()) ? resourceBindTypeMap.get(resource.getId()) : BindTypeEnum.NONE.getCode();
            node.setResourceBindType(resourceBindType);
            return node;
        }).toList();

        // 按ID建立索引，方便查找
        Map<Long, ResourceNodeDto> nodeMap = nodeList.stream()
                .collect(Collectors.toMap(ResourceNodeDto::getId, node -> node));

        // 构建树形结构
        List<ResourceNodeDto> rootNodes = new ArrayList<>();
        for (ResourceNodeDto node : nodeList) {
            Long parentId = node.getParentId();
            if (parentId == null || parentId == 0L) {
                // 根节点
                rootNodes.add(node);
            } else {
                // 子节点，添加到父节点的children中
                ResourceNodeDto parent = nodeMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(node);
                } else {
                    // 父节点不在当前列表中，作为根节点处理
                    rootNodes.add(node);
                }
            }
        }

        // 对每个节点及children进行排序
        sortResourceTree(rootNodes);

        // 对每个节点进行 ALL 绑定类型的处理
        markChildrenForAllBindType(rootNodes, resourceBindTypeMap);

        // 返回根节点列表（已排序）
        return rootNodes;
    }

    /**
     * 递归排序资源树
     */
    private static void sortResourceTree(List<ResourceNodeDto> nodeList) {
        if (CollectionUtils.isEmpty(nodeList)) {
            return;
        }

        nodeList.sort((a, b) -> {
            Integer sortA = a.getSortIndex() != null ? a.getSortIndex() : 0;
            Integer sortB = b.getSortIndex() != null ? b.getSortIndex() : 0;
            return sortA.compareTo(sortB);
        });
        
        for (ResourceNodeDto node : nodeList) {
            if (CollectionUtils.isNotEmpty(node.getChildren())) {
                sortResourceTree(node.getChildren());
            }
        }
    }

    /**
     * 对于绑定方式为 ALL 的节点，将其所有子节点也强制标记为 ALL
     * 注意：ALL 类型的资源会强制覆盖子资源的绑定类型
     */
    private static void markChildrenForAllBindType(List<ResourceNodeDto> nodes, Map<Long, Integer> resourceBindTypeMap) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        for (ResourceNodeDto node : nodes) {
            if (BindTypeEnum.ALL.getCode().equals(node.getResourceBindType())) {
                // 父节点是 ALL，强制将所有子节点也标记为 ALL
                markAllChildren(node, BindTypeEnum.ALL.getCode());
            } else if (CollectionUtils.isNotEmpty(node.getChildren())) {
                // 继续递归处理子节点
                markChildrenForAllBindType(node.getChildren(), resourceBindTypeMap);
            }
        }
    }

    /**
     * 递归将子节点强制标记为指定绑定类型
     * 注意：此方法会强制覆盖子节点的绑定类型（用于处理 ALL 类型）
     */
    private static void markAllChildren(ResourceNodeDto node, Integer resourceBindType) {
        if (CollectionUtils.isEmpty(node.getChildren())) {
            return;
        }
        for (ResourceNodeDto child : node.getChildren()) {
            // 强制设置子节点的绑定类型为 ALL（覆盖原有类型）
            // 因为父节点是 ALL 时，所有子节点也必须是 ALL
            child.setResourceBindType(resourceBindType);
            // 递归处理子节点的子节点
            markAllChildren(child, resourceBindType);
        }
    }

    private static ResourceNodeDto convertResourceToNode(SysResource resource) {
        ResourceNodeDto node = new ResourceNodeDto();
        node.setId(resource.getId());
        node.setCode(resource.getCode());
        node.setName(resource.getName());
        node.setDescription(resource.getDescription());
        node.setSource(resource.getSource());
        node.setType(resource.getType());
        node.setParentId(resource.getParentId());
        node.setPath(resource.getPath());
        node.setIcon(resource.getIcon());
        node.setSortIndex(resource.getSortIndex());
        node.setStatus(resource.getStatus());
        return node;
    }

    /**
     * 将 ResourceNodeDto 列表（扁平或已是树形）构建/规范为树形结构
     *
     * @param nodeList 资源节点列表，每个节点需包含 id 和 parentId；若已是树形则先扁平化再重建
     * @return 资源树形结构（根节点列表）
     */
    public static List<ResourceNodeDto> buildResourceTreeFromNodes(List<ResourceNodeDto> nodeList) {
        if (CollectionUtils.isEmpty(nodeList)) {
            return new ArrayList<>();
        }
        List<ResourceNodeDto> flatList = flattenToNodeListWithParentId(nodeList, null);
        return buildTreeFromFlatNodeList(flatList);
    }

    /**
     * 递归将资源树扁平化为列表，并确保每个节点的 parentId 正确
     */
    private static List<ResourceNodeDto> flattenToNodeListWithParentId(List<ResourceNodeDto> nodes, Long parentId) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>();
        }
        List<ResourceNodeDto> result = new ArrayList<>();
        for (ResourceNodeDto node : nodes) {
            ResourceNodeDto copy = new ResourceNodeDto();
            copy.setId(node.getId());
            copy.setParentId(node.getParentId() != null ? node.getParentId() : parentId);
            copy.setResourceBindType(node.getResourceBindType());
            copy.setCode(node.getCode());
            copy.setName(node.getName());
            copy.setDescription(node.getDescription());
            copy.setSource(node.getSource());
            copy.setType(node.getType());
            copy.setPath(node.getPath());
            copy.setIcon(node.getIcon());
            copy.setSortIndex(node.getSortIndex());
            copy.setStatus(node.getStatus());
            result.add(copy);

            if (CollectionUtils.isNotEmpty(node.getChildren())) {
                result.addAll(flattenToNodeListWithParentId(node.getChildren(), node.getId()));
            }
        }
        return result;
    }

    private static List<ResourceNodeDto> buildTreeFromFlatNodeList(List<ResourceNodeDto> flatList) {
        if (CollectionUtils.isEmpty(flatList)) {
            return new ArrayList<>();
        }
        Map<Long, ResourceNodeDto> nodeMap = flatList.stream()
                .filter(n -> n.getId() != null)
                .collect(Collectors.toMap(ResourceNodeDto::getId, n -> n, (a, b) -> a));

        List<ResourceNodeDto> rootNodes = new ArrayList<>();
        for (ResourceNodeDto node : flatList) {
            Long nodeParentId = node.getParentId();
            if (nodeParentId == null || nodeParentId == 0L) {
                rootNodes.add(node);
            } else {
                ResourceNodeDto parent = nodeMap.get(nodeParentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(node);
                } else {
                    rootNodes.add(node);
                }
            }
        }

        sortResourceTree(rootNodes);
        return rootNodes;
    }

}
