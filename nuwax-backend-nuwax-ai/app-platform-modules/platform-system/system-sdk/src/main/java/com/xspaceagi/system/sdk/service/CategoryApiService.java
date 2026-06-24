package com.xspaceagi.system.sdk.service;

import com.xspaceagi.system.sdk.service.dto.CategoryDto;

import java.util.List;

/**
 * 分类API服务
 */
public interface CategoryApiService {

    /**
     * 插入分类
     *
     * @param categoryDto 分类信息
     * @return 创建后的分类信息
     */
    CategoryDto insert(CategoryDto categoryDto);

    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类信息
     */
    CategoryDto getById(Long id);

    /**
     * 根据类型查询分类列表
     *
     * @param type 分类类型
     * @return 分类列表
     */
    List<CategoryDto> listByType(String type);

    /**
     * 根据租户ID查询分类列表
     *
     * @param tenantId 租户ID
     * @return 分类列表
     */
    List<CategoryDto> listByTenantId(Long tenantId);

    /**
     * 根据类型和租户ID查询分类列表
     *
     * @param type     分类类型
     * @param tenantId 租户ID
     * @return 分类列表
     */
    List<CategoryDto> listByTypeAndTenantId(String type, Long tenantId);

    /**
     * 根据编码查询分类
     *
     * @param code 分类编码
     * @return 分类信息
     */
    CategoryDto getByCode(String code);
}