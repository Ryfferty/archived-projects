package com.xspaceagi.system.application.service;

import java.util.List;

import com.xspaceagi.system.infra.dao.entity.Category;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;

/**
 * 分类管理应用服务
 */
public interface CategoryApplicationService {

    /**
     * 创建分类
     */
    ReqResult<Category> create(Category category, UserContext userContext);

    /**
     * 更新分类
     */
    ReqResult<Category> update(Category category, UserContext userContext);

    /**
     * 删除分类
     */
    ReqResult<Void> delete(Long id, UserContext userContext);

    /**
     * 根据ID查询分类
     */
    Category getById(Long id);

    /**
     * 根据类型查询分类列表
     */
    List<Category> listByType(String type);

    /**
     * 根据租户ID查询分类列表
     */
    List<Category> listByTenantId(Long tenantId);

    /**
     * 根据类型和租户ID查询分类列表
     */
    List<Category> listByTypeAndTenantId(String type, Long tenantId);

    /**
     * 根据编码查询分类
     */
    Category getByCode(String code);
}
