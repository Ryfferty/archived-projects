package com.xspaceagi.system.application.converter;

import com.xspaceagi.system.application.dto.permission.MenuNodeDto;
import com.xspaceagi.system.application.dto.permission.ResourceNodeDto;
import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.ResourceNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuNode 转换为 MenuNodeDto 的转换器
 */
public class MenuNodeConverter {

    public static List<MenuNodeDto> convertMenuTreeToDtoTree(List<MenuNode> menuNodeList) {
        if (CollectionUtils.isEmpty(menuNodeList)) {
            return new ArrayList<>();
        }

        List<MenuNodeDto> result = new ArrayList<>();
        for (MenuNode menuNode : menuNodeList) {
            MenuNodeDto dto = new MenuNodeDto();
            BeanUtils.copyProperties(menuNode, dto);

            // 转换资源树（资源详细信息已在application层填充）
            if (CollectionUtils.isNotEmpty(menuNode.getResourceTree())) {
                dto.setResourceTree(convertResourceTree(menuNode.getResourceTree()));
            }

            // 递归转换children
            if (CollectionUtils.isNotEmpty(menuNode.getChildren())) {
                dto.setChildren(convertMenuTreeToDtoTree(menuNode.getChildren()));
            }

            result.add(dto);
        }

        return result;
    }

    private static List<ResourceNodeDto> convertResourceTree(
            List<ResourceNode> resourceNodes) {
        if (CollectionUtils.isEmpty(resourceNodes)) {
            return new ArrayList<>();
        }

        List<ResourceNodeDto> result = new ArrayList<>();
        for (ResourceNode node : resourceNodes) {
            ResourceNodeDto dto = new ResourceNodeDto();
            dto.setId(node.getId());
            dto.setResourceBindType(node.getResourceBindType());

            // 资源详细信息已在application层填充，直接复制
            dto.setCode(node.getCode());
            dto.setName(node.getName());
            dto.setDescription(node.getDescription());
            dto.setSource(node.getSource());
            dto.setType(node.getType());
            dto.setParentId(node.getParentId());
            dto.setPath(node.getPath());
            dto.setIcon(node.getIcon());
            dto.setSortIndex(node.getSortIndex());
            dto.setStatus(node.getStatus());

            // 递归转换children
            if (CollectionUtils.isNotEmpty(node.getChildren())) {
                dto.setChildren(convertResourceTree(node.getChildren()));
            }

            result.add(dto);
        }

        return result;
    }

}

