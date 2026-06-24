package com.xspaceagi.system.domain.service.impl;

import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.ResourceNode;
import com.xspaceagi.system.domain.service.SysFlattenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysFlattenServiceImpl implements SysFlattenService {

    @Override
    public List<MenuNode> flattenMenuTree(List<MenuNode> tree) {
        if (CollectionUtils.isEmpty(tree)) {
            return new ArrayList<>();
        }

        List<MenuNode> result = new ArrayList<>();
        for (MenuNode node : tree) {
            if (node.getId() != null) {
                result.add(node);
            }
            if (CollectionUtils.isNotEmpty(node.getChildren())) {
                result.addAll(flattenMenuTree(node.getChildren()));
            }
        }
        return result;
    }

    @Override
    public List<ResourceNode> flattenResourceTree(List<ResourceNode> tree) {
        if (CollectionUtils.isEmpty(tree)) {
            return new ArrayList<>();
        }
        List<ResourceNode> result = new ArrayList<>();
        for (ResourceNode node : tree) {
            if (node.getId() != null) {
                result.add(node);
            }
            if (CollectionUtils.isNotEmpty(node.getChildren())) {
                result.addAll(flattenResourceTree(node.getChildren()));
            }
        }
        return result;
    }

}