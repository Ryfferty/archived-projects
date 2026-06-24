package com.xspaceagi.system.application.converter;

import com.xspaceagi.system.application.dto.permission.MenuNodeDto;
import com.xspaceagi.system.application.dto.permission.ResourceNodeDto;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单树工具类
 */
public class MenuTreeUtil {

    /**
     * 构建菜单树形结构，同时将每个菜单节点的 resourceTree 规范为树形结构
     */
    public static List<MenuNodeDto> buildMenuTree(List<MenuNodeDto> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return new ArrayList<>();
        }

        // 按ID建立索引，方便查找
        Map<Long, MenuNodeDto> menuMap = menuList.stream()
                .collect(Collectors.toMap(MenuNodeDto::getId, menu -> menu));

        // 构建树形结构
        List<MenuNodeDto> rootMenus = new ArrayList<>();
        for (MenuNodeDto menu : menuList) {
            Long parentId = menu.getParentId();
            if (parentId == null || parentId == 0L) {
                // 根节点
                rootMenus.add(menu);
            } else {
                // 子节点，添加到父节点的children中
                MenuNodeDto parent = menuMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(menu);
                }
            }
        }

        // 对每个节点的children进行排序
        sortMenuTree(rootMenus);

        // 将每个菜单节点的 resourceTree 转成树形结构
        ensureResourceTreeForMenuTree(rootMenus);

        // 返回根节点列表（已排序）
        return rootMenus;
    }

    /**
     * 递归确保菜单树中每个节点的 resourceTree 均为树形结构。
     * 若存在 resourceNodes（扁平结构），则从中构建树形并 set 到 resourceTree；
     * 否则若 resourceTree 本身非标准树形，则规范为树形。
     */
    private static void ensureResourceTreeForMenuTree(List<MenuNodeDto> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return;
        }
        for (MenuNodeDto menu : menuList) {
            List<ResourceNodeDto> resourceList = CollectionUtils.isNotEmpty(menu.getResourceNodes())
                    ? menu.getResourceNodes()
                    : menu.getResourceTree();
            if (CollectionUtils.isNotEmpty(resourceList)) {
                menu.setResourceTree(ResourceTreeUtil.buildResourceTreeFromNodes(resourceList));
                // 不需要返回 resourceNodes
                menu.setResourceNodes(null);
            }
            if (CollectionUtils.isNotEmpty(menu.getChildren())) {
                ensureResourceTreeForMenuTree(menu.getChildren());
            }
        }
    }

    /**
     * 递归排序菜单树
     */
    private static void sortMenuTree(List<MenuNodeDto> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return;
        }
        menuList.sort((a, b) -> {
            Integer sortA = a.getSortIndex() != null ? a.getSortIndex() : 0;
            Integer sortB = b.getSortIndex() != null ? b.getSortIndex() : 0;
            return sortA.compareTo(sortB);
        });
        for (MenuNodeDto menu : menuList) {
            if (CollectionUtils.isNotEmpty(menu.getChildren())) {
                sortMenuTree(menu.getChildren());
            }
        }
    }
}
