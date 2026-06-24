package com.xspaceagi.custompage.domain.repository;

import java.util.List;

import com.xspaceagi.custompage.domain.model.CustomPageDomainModel;

/**
 * 自定义页面域名绑定仓储接口
 */
public interface ICustomPageDomainRepository {

    /**
     * 根据project_id查询域名列表
     */
    List<CustomPageDomainModel> listByProjectId(Long projectId);

    /**
     * 根据ID查询域名绑定
     */
    CustomPageDomainModel getById(Long id);

    /**
     * 根据域名查询
     */
    CustomPageDomainModel getByDomain(String domain);

    /**
     * 新增域名绑定
     */
    CustomPageDomainModel add(CustomPageDomainModel model);

    /**
     * 更新域名绑定
     */
    void updateById(CustomPageDomainModel model);

    /**
     * 删除域名绑定
     */
    void removeById(Long id);

    List<String> listAllDomains();
}
