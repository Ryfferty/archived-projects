package com.xspaceagi.system.domain.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.ResourceNode;
import com.xspaceagi.system.domain.service.SysMenuDomainService;
import com.xspaceagi.system.infra.dao.entity.SysMenu;
import com.xspaceagi.system.infra.dao.entity.SysMenuResource;
import com.xspaceagi.system.infra.dao.entity.SysResource;
import com.xspaceagi.system.spec.enums.BindTypeEnum;
import com.xspaceagi.system.spec.enums.ResourceTypeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xspaceagi.system.spec.jackson.JsonSerializeUtil;

/**
 * 菜单 + 资源权限计算通用逻辑（角色 / 用户组共用）
 */
public class MenuAuthHelper {

    private static Logger log = LoggerFactory.getLogger(MenuAuthHelper.class);

    /**
     * 构建带资源权限的菜单列表
     *
     * @param bindings               角色或用户组的菜单绑定列表（如 SysRoleMenu / SysGroupMenu）
     * @param menuIdGetter           从绑定对象获取 menuId 的函数
     * @param menuBindTypeGetter     从绑定对象获取 menuBindType 的函数
     * @param resourceTreeJsonGetter 从绑定对象获取 resourceTreeJson 的函数
     * @param allMenus               全量有效菜单
     * @param allResources           全量有效资源
     * @param sysMenuDomainService   菜单领域服务（用于查询 sys_menu_resource）
     */
    public static <B> List<MenuNode> buildMenuListWithAuth(List<B> bindings,
                                                           Function<B, Long> menuIdGetter,
                                                           Function<B, Integer> menuBindTypeGetter,
                                                           Function<B, String> resourceTreeJsonGetter,
                                                           List<SysMenu> allMenus,
                                                           List<SysResource> allResources,
                                                           SysMenuDomainService sysMenuDomainService) {
        if (CollectionUtils.isEmpty(allMenus)) {
            return new ArrayList<>();
        }

        // 绑定关系：menuId -> 绑定记录
        Map<Long, B> bindingMap = CollectionUtils.isEmpty(bindings)
                ? Collections.emptyMap()
                : bindings.stream().collect(Collectors.toMap(
                        menuIdGetter,
                        b -> b,
                        (a, b) -> a
                ));

        // 菜单ID -> 菜单实体
        Map<Long, SysMenu> allMenuMap = allMenus.stream().collect(Collectors.toMap(SysMenu::getId, m -> m));

        // 1. 先根据绑定记录，得到每个菜单的“初始” menuBindType（无记录视为 NONE）
        Map<Long, Integer> menuBindTypeMap = new HashMap<>();
        for (SysMenu menu : allMenus) {
            B binding = bindingMap.get(menu.getId());
            Integer menuBindType = (binding != null && menuBindTypeGetter.apply(binding) != null)
                    ? menuBindTypeGetter.apply(binding)
                    : BindTypeEnum.NONE.getCode();
            menuBindTypeMap.put(menu.getId(), menuBindType);
        }

        // 2. 构建菜单树 childrenMap，按父子关系自上而下传播父菜单的 ALL
        // parentId=0 表示父节点为 root，需加入 childrenMap.get(0)，否则 root 的子菜单无法被传播
        Map<Long, List<SysMenu>> childrenMap = new HashMap<>();
        for (SysMenu menu : allMenus) {
            Long parentId = menu.getParentId();
            if (parentId == null) {
                continue;
            }
            childrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(menu);
        }

        // 从所有根菜单开始 DFS，将父菜单为 ALL 的权限向下传递给所有子菜单
        for (SysMenu menu : allMenus) {
            Long parentId = menu.getParentId();
            if (parentId == null || parentId == 0L || !allMenuMap.containsKey(parentId)) {
                propagateMenuBindType(menu, menuBindTypeMap, childrenMap);
            }
        }

        // 一次性批量查询所有菜单的菜单-资源关联，避免 N+1
        List<Long> allMenuIds = allMenus.stream()
                .map(SysMenu::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SysMenuResource> allMenuResources = sysMenuDomainService.queryResourceListByMenuIds(allMenuIds);
        Map<Long, List<SysMenuResource>> menuResourcesByMenuId = CollectionUtils.isEmpty(allMenuResources)
                ? Collections.emptyMap()
                : allMenuResources.stream().collect(Collectors.groupingBy(SysMenuResource::getMenuId));

        // 3. 构建最终返回的 MenuNode 列表
        List<MenuNode> result = new ArrayList<>();
        for (SysMenu menu : allMenus) {
            MenuNode model = new MenuNode();
            model.setId(menu.getId());

            B binding = bindingMap.get(menu.getId());
            // 使用自上而下传播后的“有效菜单绑定类型”
            Integer menuBindType = menuBindTypeMap.getOrDefault(menu.getId(), BindTypeEnum.NONE.getCode());
            model.setMenuBindType(menuBindType);

            // 构建资源树（从预加载的 menuResourcesByMenuId 取，不按菜单逐条查库）
            // allResources 为空时仍设置 resourceTree 为 []，避免语义不清的 null
            List<SysMenuResource> menuResources = menuResourcesByMenuId.getOrDefault(menu.getId(), Collections.emptyList());
            List<ResourceNode> resourceTree;
            if (!CollectionUtils.isEmpty(allResources)) {
                if (binding == null) {
                    // 这种情况是角色/用户组没有直接绑定菜单，通过传播，此菜单有可能被绑定，也可能未绑定
                    Integer defaultResourceBindType = (menuBindType != null && !BindTypeEnum.NONE.getCode().equals(menuBindType))
                            ? BindTypeEnum.ALL.getCode()
                            : BindTypeEnum.NONE.getCode();
                    resourceTree = buildResourceTreeForUnboundMenu(
                            menuResources,
                            allResources,
                            defaultResourceBindType
                    );
                } else {
                    // 这种情况是角色/用户组直接绑定了菜单
                    resourceTree = buildResourceTreeForBinding(
                            allResources,
                            binding,
                            menuIdGetter,
                            resourceTreeJsonGetter,
                            menuResources
                    );
                }
            } else {
                resourceTree = new ArrayList<>();
            }
            model.setResourceTree(resourceTree);

            result.add(model);
        }

        return result;
    }

    /**
     * 菜单绑定类型优先级：
     * NONE < ALL < PART
     * - NONE 可以被 ALL / PART 覆盖
     * - ALL 可以被 PART 覆盖
     * - PART 不会被 ALL / NONE 覆盖
     */
    private static int menuBindPriority(Integer type) {
        if (type == null) {
            return 0;
        }
        if (BindTypeEnum.NONE.getCode().equals(type)) {
            return 1;
        }
        if (BindTypeEnum.ALL.getCode().equals(type)) {
            return 2;
        }
        if (BindTypeEnum.PART.getCode().equals(type)) {
            return 3;
        }
        return 0;
    }

    private static Integer mergeMenuBindType(Integer oldType, Integer newType) {
        if (newType == null) {
            return oldType;
        }
        if (oldType == null) {
            return newType;
        }
        // 只有当新类型优先级更高时才覆盖旧类型
        return menuBindPriority(newType) > menuBindPriority(oldType) ? newType : oldType;
    }

    /**
     * 为未绑定角色/用户组的菜单构建资源树
     * 只返回菜单绑定的资源范围（由调用方传入已查询的 menuResources），所有资源统一标记为 defaultBindType
     */
    private static List<ResourceNode> buildResourceTreeForUnboundMenu(List<SysMenuResource> menuResources,
                                                                      List<SysResource> allResources,
                                                                      Integer defaultBindType) {
        if (CollectionUtils.isEmpty(allResources)) {
            return new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(menuResources)) {
            return new ArrayList<>();
        }

        // 构建资源子节点索引
        Map<Long, List<SysResource>> resourceChildrenMap = new HashMap<>();
        for (SysResource resource : allResources) {
            Long parentId = resource.getParentId();
            if (parentId != null && parentId != 0L) {
                resourceChildrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(resource);
            }
        }

        // 构建菜单有权限的资源ID集合（包括ALL类型的子资源）
        Set<Long> menuAuthorizedResourceIds = new HashSet<>();
        for (SysMenuResource mr : menuResources) {
            Long resId = mr.getResourceId();
            if (resId == null) {
                continue;
            }
            menuAuthorizedResourceIds.add(resId);

            // 如果菜单绑定的资源是ALL类型，展开其所有子资源
            if (BindTypeEnum.ALL.getCode().equals(mr.getResourceBindType())) {
                collectChildrenResourceIds(resId, resourceChildrenMap, menuAuthorizedResourceIds);
            }
        }

        // 筛选出菜单有权限的资源
        List<SysResource> menuAuthorizedResources = allResources.stream()
                .filter(resource -> menuAuthorizedResourceIds.contains(resource.getId()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(menuAuthorizedResources)) {
            return new ArrayList<>();
        }

        // 所有资源统一标记为 defaultBindType（例如：NONE 或 ALL）
        Map<Long, Integer> resourceBindTypeMap = new HashMap<>();
        for (SysResource resource : menuAuthorizedResources) {
            resourceBindTypeMap.put(resource.getId(), defaultBindType);
        }

        // 构建资源树 - 不裁剪为末级模块/叶子组件结构，返回的是完整的菜单的资源树
        // return buildResourceTree(menuAuthorizedResources, resourceBindTypeMap, defaultBindType);

        // 构建资源树并裁剪为末级模块/叶子组件结构
        List<ResourceNode> tree = buildResourceTree(menuAuthorizedResources, resourceBindTypeMap, defaultBindType);
        return pruneResourceTreeToLeafModules(tree, allResources);
    }

    /**
     * 根据绑定记录（角色 / 用户组）构建对应菜单的资源树
     * 返回菜单原本绑定的所有资源（由调用方传入已查询的 menuResources），对 resourceBindType 打标。
     */
    private static <B> List<ResourceNode> buildResourceTreeForBinding(List<SysResource> allResources,
                                                                      B binding,
                                                                      Function<B, Long> menuIdGetter,
                                                                      Function<B, String> resourceTreeJsonGetter,
                                                                      List<SysMenuResource> menuResources) {
        if (binding == null || CollectionUtils.isEmpty(allResources)) {
            return new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(menuResources)) {
            return new ArrayList<>();
        }

        // 构建资源子节点索引（resourceId -> children），用于展开 ALL 的子资源
        Map<Long, List<SysResource>> resourceChildrenMap = new HashMap<>();
        for (SysResource resource : allResources) {
            Long parentId = resource.getParentId();
            if (parentId == null || parentId == 0L) {
                continue;
            }
            resourceChildrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(resource);
        }

        // 构建菜单“最终有权限”的资源ID集合：
        //     - 所有在 sys_menu_resource 中出现的 resourceId
        //     - 对于其中 resourceBindType=ALL 的资源，展开其所有子资源，一并加入
        Set<Long> menuAuthorizedResourceIds = new HashSet<>();
        for (SysMenuResource mr : menuResources) {
            Long resId = mr.getResourceId();
            if (resId == null) {
                continue;
            }
            menuAuthorizedResourceIds.add(resId);

            // 如果菜单侧对该资源的 resourceBindType=ALL，则其所有子资源都算作菜单有权限
            if (BindTypeEnum.ALL.getCode().equals(mr.getResourceBindType())) {
                collectChildrenResourceIds(resId, resourceChildrenMap, menuAuthorizedResourceIds);
            }
        }

        // 从所有资源中筛选出菜单有权限的资源（只在这些资源范围内构建资源树）
        List<SysResource> menuAuthorizedResources = allResources.stream()
                .filter(resource -> menuAuthorizedResourceIds.contains(resource.getId()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(menuAuthorizedResources)) {
            return new ArrayList<>();
        }

        // 从绑定实体存储的 JSON 中反序列化出原始的资源节点树
        List<ResourceNode> storedTree = null;
        String json = resourceTreeJsonGetter.apply(binding);
        if (StringUtils.isNotBlank(json)) {
            try {
                storedTree = JsonSerializeUtil.parseObject(json, new TypeReference<List<ResourceNode>>() {});
            } catch (Exception e) {
                log.error("反序列化 resourceTreeJson 失败: " + e.getMessage(), e);
            }
        }

        // 将反序列化出来的资源节点树扁平化为 resourceId -> 绑定实体的资源绑定类型 的映射
        Map<Long, Integer> ownerResourceBindTypeMap = storedTree == null
                ? new HashMap<>()
                : buildBindTypeMapFromResourceTree(storedTree);

        // 根据 resourceTreeJson 中的记录来确定每个资源的最终绑定类型
        //    如果 resourceTreeJson 为空，则所有资源都是 NONE
        Map<Long, Integer> finalResourceBindTypeMap = new HashMap<>();

        for (SysResource resource : menuAuthorizedResources) {
            Long resourceId = resource.getId();
            Integer bindType = ownerResourceBindTypeMap.get(resourceId);
            if (bindType != null) {
                // 在 resourceTreeJson 中有记录，使用记录中的绑定类型
                finalResourceBindTypeMap.put(resourceId, bindType);
            } else {
                // 菜单有权限但绑定实体没有授权：返回 NONE
                finalResourceBindTypeMap.put(resourceId, BindTypeEnum.NONE.getCode());
            }
        }

        // 传播资源绑定类型：ALL的子节点也是ALL，NONE的子节点也是NONE
        //    必须按「父节点优先」顺序迭代，确保父节点先于子节点被处理，这样父节点的传播才能正确覆盖子节点
        Set<Long> authorizedIds = new HashSet<>(menuAuthorizedResourceIds);
        List<SysResource> sortedByDepth = sortResourcesByDepth(menuAuthorizedResources, authorizedIds);
        Set<Long> processedResourceIds = new HashSet<>();
        for (SysResource resource : sortedByDepth) {
            Long resourceId = resource.getId();
            Integer bindType = finalResourceBindTypeMap.get(resourceId);
            if (bindType != null && !processedResourceIds.contains(resourceId)) {
                if (BindTypeEnum.ALL.getCode().equals(bindType) || BindTypeEnum.NONE.getCode().equals(bindType)) {
                    propagateResourceBindType(resourceId, resourceChildrenMap, finalResourceBindTypeMap,
                            processedResourceIds, bindType);
                }
            }
        }

        // 返回菜单绑定的所有资源，不过滤 NONE；无权限的打标为 NONE，有权限的按实际 resourceBindType

        // 构建资源树 - 不裁剪为末级模块/叶子组件结构，返回的是完整的菜单的资源树
        // return buildResourceTree(menuAuthorizedResources, finalResourceBindTypeMap, BindTypeEnum.NONE.getCode());

        List<ResourceNode> tree = buildResourceTree(menuAuthorizedResources, finalResourceBindTypeMap,
                BindTypeEnum.NONE.getCode());
        return pruneResourceTreeToLeafModules(tree, allResources);
    }

    /**
     * 将资源树扁平化，构建 resourceId -> resourceBindType 的映射
     */
    private static Map<Long, Integer> buildBindTypeMapFromResourceTree(List<ResourceNode> nodes) {
        Map<Long, Integer> map = new HashMap<>();
        if (CollectionUtils.isEmpty(nodes)) {
            return map;
        }
        for (ResourceNode node : nodes) {
            if (node.getId() != null && node.getResourceBindType() != null) {
                map.put(node.getId(), node.getResourceBindType());
            }
            if (!CollectionUtils.isEmpty(node.getChildren())) {
                map.putAll(buildBindTypeMapFromResourceTree(node.getChildren()));
            }
        }
        return map;
    }

    /**
     * 按规则裁剪资源树，规则见 pruneByLevel 注释
     */
    private static List<ResourceNode> pruneResourceTreeToLeafModules(List<ResourceNode> resourceTree,
                                                                     List<SysResource> allResources) {
        if (CollectionUtils.isEmpty(resourceTree) || CollectionUtils.isEmpty(allResources)) {
            return resourceTree;
        }
        return pruneByLevel(resourceTree);
    }

    /**
     * 按层级递归裁剪，规则：
     * 1、多个同级模块/组件：返回所有节点本身及子节点，树形
     * 2、单个模块：无子节点→返回模块本身；有子节点且无同级→只返回子节点，递归处理子节点
     *    （1）子节点有组件：返回该级所有节点，模块按树形
     *    （2）子节点只有模块：递归（多同级 or 单同级）
     * 3、只有组件：返回组件
     */
    private static List<ResourceNode> pruneByLevel(List<ResourceNode> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>();
        }

        boolean allOperation = nodes.stream()
                .filter(n -> n != null && n.getId() != null)
                .allMatch(n -> ResourceTypeEnum.OPERATION.getCode().equals(n.getType()));

        if (allOperation) {
            return nodes.stream()
                    .filter(n -> n != null && n.getId() != null)
                    .map(MenuAuthHelper::copyNodeWithoutChildren)
                    .collect(Collectors.toList());
        }

        if (nodes.size() > 1) {
            return nodes.stream()
                    .filter(n -> n != null && n.getId() != null)
                    .map(MenuAuthHelper::copyNodeWithSubtree)
                    .collect(Collectors.toList());
        }

        ResourceNode node = nodes.get(0);
        if (node == null || node.getId() == null) {
            return new ArrayList<>();
        }
        if (ResourceTypeEnum.OPERATION.getCode().equals(node.getType())) {
            return Collections.singletonList(copyNodeWithoutChildren(node));
        }

        List<ResourceNode> children = node.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            return Collections.singletonList(copyNodeWithoutChildren(node));
        }

        return pruneByLevel(children);
    }

    private static ResourceNode copyNodeWithoutChildren(ResourceNode node) {
        ResourceNode copy = new ResourceNode();
        copy.setId(node.getId());
        copy.setParentId(node.getParentId());
        copy.setResourceBindType(node.getResourceBindType());
        copy.setType(node.getType());
        copy.setCode(node.getCode());
        copy.setName(node.getName());
        return copy;
    }

    /**
     * 深拷贝节点及其子树，子模块按树形保留
     */
    private static ResourceNode copyNodeWithSubtree(ResourceNode node) {
        ResourceNode copy = copyNodeWithoutChildren(node);
        List<ResourceNode> children = node.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            return copy;
        }
        List<ResourceNode> copiedChildren = new ArrayList<>();
        for (ResourceNode child : children) {
            if (child == null || child.getId() == null) {
                continue;
            }
            if (ResourceTypeEnum.OPERATION.getCode().equals(child.getType())) {
                copiedChildren.add(copyNodeWithoutChildren(child));
            } else if (ResourceTypeEnum.MODULE.getCode().equals(child.getType())) {
                copiedChildren.add(copyNodeWithSubtree(child));
            }
        }
        if (!copiedChildren.isEmpty()) {
            copy.setChildren(copiedChildren);
        }
        return copy;
    }

    /**
     * 根据完整资源列表和绑定关系，构建资源树（仅使用资源ID和绑定类型）
     *
     * @param allResources        所有资源
     * @param resourceBindTypeMap 资源ID到绑定类型的映射，可为空
     * @param defaultBindType     未出现在映射中的资源默认绑定类型
     */
    private static List<ResourceNode> buildResourceTree(List<SysResource> allResources,
                                                        Map<Long, Integer> resourceBindTypeMap,
                                                        Integer defaultBindType) {
        if (CollectionUtils.isEmpty(allResources)) {
            return new ArrayList<>();
        }

        // 先为每个资源构建对应的节点
        Map<Long, ResourceNode> nodeMap = new HashMap<>();
        for (SysResource resource : allResources) {
            ResourceNode node = new ResourceNode();
            node.setId(resource.getId());
            node.setType(resource.getType());
            node.setParentId(resource.getParentId());
            Integer bt = resourceBindTypeMap != null ? resourceBindTypeMap.get(resource.getId()) : null;
            node.setResourceBindType(bt != null ? bt : defaultBindType);
            nodeMap.put(resource.getId(), node);
        }

        // 再根据 parentId 组装树结构
        List<ResourceNode> roots = new ArrayList<>();
        for (SysResource resource : allResources) {
            ResourceNode current = nodeMap.get(resource.getId());
            Long parentId = resource.getParentId();
            if (parentId == null || parentId == 0L || !nodeMap.containsKey(parentId)) {
                roots.add(current);
            } else {
                ResourceNode parent = nodeMap.get(parentId);
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(current);
            }
        }
        return roots;
    }

    /**
     * 递归收集某个资源节点下的所有子资源ID
     */
    private static void collectChildrenResourceIds(Long resourceId,
                                                   Map<Long, List<SysResource>> resourceChildrenMap,
                                                   Set<Long> idSet) {
        List<SysResource> children = resourceChildrenMap.get(resourceId);
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        for (SysResource child : children) {
            if (child.getId() == null) {
                continue;
            }
            if (idSet.add(child.getId())) {
                collectChildrenResourceIds(child.getId(), resourceChildrenMap, idSet);
            }
        }
    }

    /**
     * 按资源树深度排序，确保父节点在子节点之前（用于传播时父节点先处理）
     */
    private static List<SysResource> sortResourcesByDepth(List<SysResource> resources, Set<Long> authorizedIds) {
        if (CollectionUtils.isEmpty(resources)) {
            return resources;
        }
        // 使用手动构建 Map，因为 Collectors.toMap 不允许 null 值，而 parentId 可能为 null（根节点）
        Map<Long, Long> parentIdMap = new HashMap<>();
        for (SysResource r : resources) {
            if (r.getId() != null) {
                parentIdMap.put(r.getId(), r.getParentId());
            }
        }
        Map<Long, Integer> depthMap = new HashMap<>();
        for (SysResource r : resources) {
            if (r.getId() != null) {
                computeDepth(r.getId(), parentIdMap, authorizedIds, depthMap);
            }
        }
        return resources.stream()
                .sorted((a, b) -> Integer.compare(
                        depthMap.getOrDefault(a.getId(), 0),
                        depthMap.getOrDefault(b.getId(), 0)))
                .collect(Collectors.toList());
    }

    private static int computeDepth(Long resourceId, Map<Long, Long> parentIdMap, Set<Long> authorizedIds,
                                    Map<Long, Integer> depthMap) {
        if (resourceId == null) {
            return 0;
        }
        if (depthMap.containsKey(resourceId)) {
            return depthMap.get(resourceId);
        }
        Long parentId = parentIdMap.get(resourceId);
        int depth;
        if (parentId == null || parentId == 0L || !authorizedIds.contains(parentId)) {
            depth = 0;
        } else {
            depth = 1 + computeDepth(parentId, parentIdMap, authorizedIds, depthMap);
        }
        depthMap.put(resourceId, depth);
        return depth;
    }

    /**
     * 递归传播资源绑定类型：
     * - 如果当前资源是 ALL，则所有子资源都强制为 ALL
     * - 如果当前资源是 NONE，则所有子资源都强制为 NONE
     * - PART 类型不传播，子资源保持自己的绑定类型
     */
    private static void propagateResourceBindType(Long resourceId,
                                                  Map<Long, List<SysResource>> resourceChildrenMap,
                                                  Map<Long, Integer> resourceBindTypeMap,
                                                  Set<Long> processedResourceIds,
                                                  Integer bindType) {
        if (processedResourceIds.contains(resourceId)) {
            return;
        }
        processedResourceIds.add(resourceId);

        List<SysResource> children = resourceChildrenMap.get(resourceId);
        if (CollectionUtils.isEmpty(children)) {
            return;
        }

        for (SysResource child : children) {
            if (child.getId() == null) {
                continue;
            }
            // 强制设置子资源的绑定类型
            resourceBindTypeMap.put(child.getId(), bindType);
            // 递归处理子资源的子资源
            propagateResourceBindType(child.getId(), resourceChildrenMap, resourceBindTypeMap, 
                    processedResourceIds, bindType);
        }
    }

    /**
     * 自上而下传播菜单绑定类型：
     * - 如果当前菜单是 ALL，则所有子菜单都强制为 ALL
     * - 如果当前菜单是 NONE，则所有子菜单都强制为 NONE
     * - 如果当前菜单是 PART，子菜单保持自己的 menuBindType（按数据记录的实际值）
     */
    private static void propagateMenuBindType(SysMenu menu,
                                              Map<Long, Integer> menuBindTypeMap,
                                              Map<Long, List<SysMenu>> childrenMap) {
        if (menu == null) {
            return;
        }
        Integer currentType = menuBindTypeMap.get(menu.getId());
        if (currentType == null) {
            currentType = BindTypeEnum.NONE.getCode();
            menuBindTypeMap.put(menu.getId(), currentType);
        }

        List<SysMenu> children = childrenMap.get(menu.getId());
        if (CollectionUtils.isEmpty(children)) {
            return;
        }

        for (SysMenu child : children) {
            Integer childCurrent = menuBindTypeMap.get(child.getId());

            if (BindTypeEnum.ALL.getCode().equals(currentType)) {
                // 父菜单 ALL：尝试将子菜单升级为 ALL（遵循优先级合并规则）
                Integer merged = mergeMenuBindType(childCurrent, BindTypeEnum.ALL.getCode());
                menuBindTypeMap.put(child.getId(), merged);
            } else if (BindTypeEnum.NONE.getCode().equals(currentType)) {
                // 父菜单 NONE：只在子菜单当前为 NONE（或未设置）时才设置为 NONE，
                // 避免覆盖子菜单在绑定记录中已有的 ALL / PART
                if (childCurrent == null
                        || BindTypeEnum.NONE.getCode().equals(childCurrent)) {
                    Integer merged = mergeMenuBindType(childCurrent, BindTypeEnum.NONE.getCode());
                    menuBindTypeMap.put(child.getId(), merged);
                }
            }
            // PART 类型不传播，子菜单保持自己的 menuBindType
            propagateMenuBindType(child, menuBindTypeMap, childrenMap);
        }
    }
}

