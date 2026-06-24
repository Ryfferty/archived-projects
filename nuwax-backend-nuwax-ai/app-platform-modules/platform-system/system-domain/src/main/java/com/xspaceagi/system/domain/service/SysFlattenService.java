package com.xspaceagi.system.domain.service;

import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.ResourceNode;

import java.util.List;

public interface SysFlattenService {

    List<MenuNode> flattenMenuTree(List<MenuNode> tree);

    List<ResourceNode> flattenResourceTree(List<ResourceNode> tree);
}