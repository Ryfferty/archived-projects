//package com.xspaceagi.system.web.converter;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.apache.commons.collections4.CollectionUtils;
//
//import com.xspaceagi.system.domain.model.MenuNode;
//import com.xspaceagi.system.domain.model.ResourceNode;
//import com.xspaceagi.system.infra.dao.entity.SysMenu;
//import com.xspaceagi.system.infra.dao.entity.SysResource;
//import com.xspaceagi.system.spec.enums.BindTypeEnum;
//
///**
// * 用户维度菜单 + 资源权限合并工具
// */
//public class UserMenuMergeUtil {
//
//    /**
//     * 合并来自多个角色 / 用户组的菜单权限
//     * - 同一 menuId 的菜单进行合并
//     * - menuBindType 取"最大权限"（ALL > PART > NONE）
//     * - 资源权限按 resourceId 合并，resourceBindType 同样取最大权限
//     */
//    public static List<MenuNode> mergeUserMenuNodes(List<MenuNode> nodes) {
//        if (CollectionUtils.isEmpty(nodes)) {
//            return new ArrayList<>();
//        }
//
//        // 按 menuId 分组
//        Map<Long, List<MenuNode>> menuGroup = nodes.stream()
//                .filter(n -> n.getMenuId() != null)
//                .collect(Collectors.groupingBy(MenuNode::getMenuId));
//
//        List<MenuNode> result = new ArrayList<>();
//        for (Map.Entry<Long, List<MenuNode>> entry : menuGroup.entrySet()) {
//            Long menuId = entry.getKey();
//            List<MenuNode> menuNodes = entry.getValue();
//            if (CollectionUtils.isEmpty(menuNodes)) {
//                continue;
//            }
//
//            MenuNode merged = new MenuNode();
//            merged.setMenuId(menuId);
//
//            // 1. 合并 menuBindType（取最大权限）
//            Integer mergedMenuBindType = menuNodes.stream()
//                    .map(MenuNode::getMenuBindType)
//                    .filter(Objects::nonNull)
//                    .max(Comparator.comparingInt(UserMenuMergeUtil::bindTypeOrder))
//                    .orElse(BindTypeEnum.NONE.getCode());
//            merged.setMenuBindType(mergedMenuBindType);
//
//            // 2. 合并资源权限（合并所有资源树）
//            List<ResourceNode> allResources = menuNodes.stream()
//                    .filter(n -> CollectionUtils.isNotEmpty(n.getResourceTree()))
//                    .flatMap(n -> flattenResourceTree(n.getResourceTree()).stream())
//                    .collect(Collectors.toList());
//
//            if (CollectionUtils.isNotEmpty(allResources)) {
//                Map<Long, List<ResourceNode>> resourceGroup = allResources.stream()
//                        .filter(r -> r.getResourceId() != null)
//                        .collect(Collectors.groupingBy(ResourceNode::getResourceId));
//
//                List<ResourceNode> mergedResourceList = new ArrayList<>();
//                for (Map.Entry<Long, List<ResourceNode>> re : resourceGroup.entrySet()) {
//                    Long resourceId = re.getKey();
//                    List<ResourceNode> resourceNodes = re.getValue();
//                    if (CollectionUtils.isEmpty(resourceNodes)) {
//                        continue;
//                    }
//                    ResourceNode mergedResource = new ResourceNode();
//                    mergedResource.setResourceId(resourceId);
//                    Integer mergedBindType = resourceNodes.stream()
//                            .map(ResourceNode::getResourceBindType)
//                            .filter(Objects::nonNull)
//                            .max(Comparator.comparingInt(UserMenuMergeUtil::bindTypeOrder))
//                            .orElse(BindTypeEnum.NONE.getCode());
//                    mergedResource.setResourceBindType(mergedBindType);
//                    mergedResourceList.add(mergedResource);
//                }
//
//                // 保存扁平化的资源列表（后续会重建资源树）
//                // 先保存到resourceTree中，后续会重建树形结构
//                merged.setResourceTree(mergedResourceList);
//            }
//
//            result.add(merged);
//        }
//
//        return result;
//    }
//
//    /**
//     * 传播菜单和资源的绑定类型，并过滤出有权限的菜单和资源
//     *
//     * @param mergedMenuNodes 合并后的菜单节点列表
//     * @param allMenus 所有菜单列表
//     * @param allResources 所有资源列表
//     * @return 有权限的菜单和资源（已传播并过滤）
//     */
//    public static List<MenuNode> propagateAndFilterAuthorizedMenus(List<MenuNode> mergedMenuNodes,
//                                                                    List<SysMenu> allMenus,
//                                                                    List<SysResource> allResources) {
//        if (CollectionUtils.isEmpty(mergedMenuNodes)) {
//            return new ArrayList<>();
//        }
//
//        // 构建菜单ID到MenuNode的映射
//        Map<Long, MenuNode> menuNodeMap = mergedMenuNodes.stream()
//                .filter(n -> n.getMenuId() != null)
//                .collect(Collectors.toMap(MenuNode::getMenuId, n -> n));
//
//        // 构建菜单父子关系映射
//        Map<Long, List<SysMenu>> menuChildrenMap = new HashMap<>();
//        for (SysMenu menu : allMenus) {
//            Long parentId = menu.getParentId();
//            if (parentId != null && parentId != 0L) {
//                menuChildrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(menu);
//            }
//        }
//
//        // 构建资源父子关系映射
//        Map<Long, List<SysResource>> resourceChildrenMap = new HashMap<>();
//        Map<Long, SysResource> resourceMap = allResources.stream()
//                .collect(Collectors.toMap(SysResource::getId, r -> r));
//        for (SysResource resource : allResources) {
//            Long parentId = resource.getParentId();
//            if (parentId != null && parentId != 0L) {
//                resourceChildrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(resource);
//            }
//        }
//
//        // 1. 传播菜单绑定类型（ALL/NONE的子菜单也传播）
//        Map<Long, Integer> menuBindTypeMap = new HashMap<>();
//        Set<Long> processedMenuIds = new HashSet<>();
//
//        // 先设置合并后的菜单绑定类型
//        for (MenuNode menuNode : mergedMenuNodes) {
//            if (menuNode.getMenuId() != null) {
//                menuBindTypeMap.put(menuNode.getMenuId(), menuNode.getMenuBindType());
//            }
//        }
//
//        // 对于ALL类型的菜单，递归设置所有子菜单为ALL
//        for (MenuNode menuNode : mergedMenuNodes) {
//            if (menuNode.getMenuId() != null && BindTypeEnum.ALL.getCode().equals(menuNode.getMenuBindType())) {
//                propagateMenuBindType(menuNode.getMenuId(), menuChildrenMap, menuBindTypeMap,
//                        processedMenuIds, BindTypeEnum.ALL.getCode());
//            }
//        }
//
//        // 对于NONE类型的菜单，递归设置所有子菜单为NONE
//        for (MenuNode menuNode : mergedMenuNodes) {
//            if (menuNode.getMenuId() != null && BindTypeEnum.NONE.getCode().equals(menuNode.getMenuBindType())) {
//                propagateMenuBindType(menuNode.getMenuId(), menuChildrenMap, menuBindTypeMap,
//                        processedMenuIds, BindTypeEnum.NONE.getCode());
//            }
//        }
//
//        // 2. 收集所有有权限的菜单ID（包括传播后的子菜单）
//        Set<Long> authorizedMenuIds = new HashSet<>();
//        for (Map.Entry<Long, Integer> entry : menuBindTypeMap.entrySet()) {
//            Integer bindType = entry.getValue();
//            if (!BindTypeEnum.NONE.getCode().equals(bindType)) {
//                authorizedMenuIds.add(entry.getKey());
//            }
//        }
//
//        // 3. 处理每个菜单的资源权限
//        List<MenuNode> result = new ArrayList<>();
//        for (Long menuId : authorizedMenuIds) {
//            MenuNode menuNode = menuNodeMap.get(menuId);
//
//            // 处理资源权限
//            List<ResourceNode> authorizedResources = new ArrayList<>();
//            if (menuNode != null && CollectionUtils.isNotEmpty(menuNode.getResourceTree())) {
//                authorizedResources = processAuthorizedResources(
//                        menuNode, resourceMap, resourceChildrenMap);
//            }
//
//            // 只返回有权限的菜单（menuBindType != NONE）
//            MenuNode authorizedMenu = new MenuNode();
//            authorizedMenu.setMenuId(menuId);
//            authorizedMenu.setResourceTree(authorizedResources); // 只包含有权限的资源（resourceBindType != NONE）
//            result.add(authorizedMenu);
//        }
//
//        return result;
//    }
//
//    /**
//     * 传播菜单绑定类型
//     */
//    private static void propagateMenuBindType(Long menuId, Map<Long, List<SysMenu>> menuChildrenMap,
//                                               Map<Long, Integer> menuBindTypeMap, Set<Long> processedMenuIds,
//                                               Integer bindType) {
//        if (processedMenuIds.contains(menuId)) {
//            return;
//        }
//        processedMenuIds.add(menuId);
//
//        List<SysMenu> children = menuChildrenMap.get(menuId);
//        if (CollectionUtils.isEmpty(children)) {
//            return;
//        }
//
//        for (SysMenu child : children) {
//            if (child.getId() == null) {
//                continue;
//            }
//            // 强制设置子菜单的绑定类型
//            menuBindTypeMap.put(child.getId(), bindType);
//            // 递归处理子菜单的子菜单
//            propagateMenuBindType(child.getId(), menuChildrenMap, menuBindTypeMap, processedMenuIds, bindType);
//        }
//    }
//
//    /**
//     * 处理菜单的资源权限，传播并过滤出有权限的资源
//     */
//    private static List<ResourceNode> processAuthorizedResources(MenuNode menuNode,
//                                                                 Map<Long, SysResource> resourceMap,
//                                                                 Map<Long, List<SysResource>> resourceChildrenMap) {
//        if (CollectionUtils.isEmpty(menuNode.getResourceTree())) {
//            return new ArrayList<>();
//        }
//
//        // 扁平化资源树（可能是树形结构或扁平列表）
//        List<ResourceNode> flatResources = flattenResourceTree(menuNode.getResourceTree());
//
//        // 构建资源绑定类型映射
//        Map<Long, Integer> resourceBindTypeMap = flatResources.stream()
//                .filter(r -> r.getResourceId() != null && r.getResourceBindType() != null)
//                .collect(Collectors.toMap(ResourceNode::getResourceId, ResourceNode::getResourceBindType));
//
//        // 传播资源绑定类型（ALL/NONE的子资源也传播）
//        Set<Long> processedResourceIds = new HashSet<>();
//        for (ResourceNode resourceNode : flatResources) {
//            Long resourceId = resourceNode.getResourceId();
//            Integer bindType = resourceNode.getResourceBindType();
//            if (resourceId != null && bindType != null && !processedResourceIds.contains(resourceId)) {
//                if (BindTypeEnum.ALL.getCode().equals(bindType) || BindTypeEnum.NONE.getCode().equals(bindType)) {
//                    propagateResourceBindType(resourceId, resourceChildrenMap, resourceBindTypeMap,
//                            processedResourceIds, bindType);
//                }
//            }
//        }
//
//        // 收集所有有权限的资源ID（resourceBindType != NONE）
//        Set<Long> authorizedResourceIds = resourceBindTypeMap.entrySet().stream()
//                .filter(e -> !BindTypeEnum.NONE.getCode().equals(e.getValue()))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toSet());
//
//        if (CollectionUtils.isEmpty(authorizedResourceIds)) {
//            return new ArrayList<>();
//        }
//
//        // 从所有资源中筛选出有权限的资源
//        List<SysResource> authorizedResources = resourceMap.values().stream()
//                .filter(r -> authorizedResourceIds.contains(r.getId()))
//                .collect(Collectors.toList());
//
//        // 重建资源树（不包含resourceBindType字段）
//        return rebuildResourceTree(authorizedResources, resourceMap, resourceChildrenMap);
//    }
//
//    /**
//     * 传播资源绑定类型
//     */
//    private static void propagateResourceBindType(Long resourceId,
//                                                  Map<Long, List<SysResource>> resourceChildrenMap,
//                                                  Map<Long, Integer> resourceBindTypeMap,
//                                                  Set<Long> processedResourceIds,
//                                                  Integer bindType) {
//        if (processedResourceIds.contains(resourceId)) {
//            return;
//        }
//        processedResourceIds.add(resourceId);
//
//        List<SysResource> children = resourceChildrenMap.get(resourceId);
//        if (CollectionUtils.isEmpty(children)) {
//            return;
//        }
//
//        for (SysResource child : children) {
//            if (child.getId() == null) {
//                continue;
//            }
//            // 强制设置子资源的绑定类型
//            resourceBindTypeMap.put(child.getId(), bindType);
//            // 递归处理子资源的子资源
//            propagateResourceBindType(child.getId(), resourceChildrenMap, resourceBindTypeMap,
//                    processedResourceIds, bindType);
//        }
//    }
//
//    /**
//     * 重建资源树（只包含有权限的资源，不设置resourceBindType字段）
//     */
//    private static List<ResourceNode> rebuildResourceTree(List<SysResource> authorizedResources,
//                                                          Map<Long, SysResource> resourceMap,
//                                                          Map<Long, List<SysResource>> resourceChildrenMap) {
//        if (CollectionUtils.isEmpty(authorizedResources)) {
//            return new ArrayList<>();
//        }
//
//        // 构建资源ID到ResourceNode的映射（不设置resourceBindType）
//        Map<Long, ResourceNode> nodeMap = new HashMap<>();
//        for (SysResource resource : authorizedResources) {
//            ResourceNode node = new ResourceNode();
//            node.setResourceId(resource.getId());
//            // 不设置resourceBindType，因为用户查询只返回有权限的资源
//            nodeMap.put(resource.getId(), node);
//        }
//
//        // 构建树形结构
//        List<ResourceNode> rootNodes = new ArrayList<>();
//        for (SysResource resource : authorizedResources) {
//            ResourceNode current = nodeMap.get(resource.getId());
//            Long parentId = resource.getParentId();
//            if (parentId == null || parentId == 0L || !nodeMap.containsKey(parentId)) {
//                // 根节点
//                rootNodes.add(current);
//            } else {
//                // 子节点，添加到父节点的children中
//                ResourceNode parent = nodeMap.get(parentId);
//                if (parent.getChildren() == null) {
//                    parent.setChildren(new ArrayList<>());
//                }
//                parent.getChildren().add(current);
//            }
//        }
//
//        return rootNodes;
//    }
//
//    /**
//     * 过滤掉完全没有权限的菜单：
//     * - menuBindType=NONE
//     * - 且所有资源 resourceBindType=NONE 或无资源
//     */
//    public static List<MenuNode> filterNoPermissionMenus(List<MenuNode> nodes) {
//        if (CollectionUtils.isEmpty(nodes)) {
//            return new ArrayList<>();
//        }
//
//        return nodes.stream()
//                .filter(node -> {
//                    Integer menuBindType = node.getMenuBindType();
//                    if (!BindTypeEnum.NONE.getCode().equals(menuBindType)) {
//                        return true;
//                    }
//                    // menuBindType=NONE，再看资源
//                    if (CollectionUtils.isEmpty(node.getFlattenResourceList())) {
//                        return false;
//                    }
//                    // 如果存在任意一个资源的绑定类型 != NONE，则保留菜单
//                    return node.getFlattenResourceList().stream()
//                            .anyMatch(r -> !BindTypeEnum.NONE.getCode().equals(r.getResourceBindType()));
//                })
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * 绑定类型优先级：NONE < PART < ALL
//     */
//    private static int bindTypeOrder(Integer bindType) {
//        if (bindType == null) {
//            return 0;
//        }
//        if (BindTypeEnum.NONE.getCode().equals(bindType)) {
//            return 1;
//        }
//        if (BindTypeEnum.PART.getCode().equals(bindType)) {
//            return 2;
//        }
//        if (BindTypeEnum.ALL.getCode().equals(bindType)) {
//            return 3;
//        }
//        return 0;
//    }
//}
//
