package com.xspaceagi.system.application.converter;

import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.application.dto.permission.MenuNodeDto;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单绑定模型转换工具类
 */
public class MenuBindModelConverterUtil {

    /**
     * 将菜单节点列表转换为MenuBindResource列表
     */
    public static List<MenuNode> convertMenuNodes(List<MenuNodeDto> menuNodeDtos) {
        if (CollectionUtils.isEmpty(menuNodeDtos)) {
            return new ArrayList<>();
        }

        List<MenuNode> menuNodes = new ArrayList<>();
        for (MenuNodeDto menuNodeDto : menuNodeDtos) {
            if (menuNodeDto.getId() == null) {
                continue;
            }
            MenuNode menuNode = MenuBindResourceModelConverter.convertToMenuNode(menuNodeDto);
            menuNodes.add(menuNode);
        }
        return menuNodes;
    }


}
