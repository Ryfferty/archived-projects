package com.xspaceagi.custompage.application.service;

import com.xspaceagi.custompage.domain.model.CustomPageDomainModel;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;

import java.util.List;

/**
 * 自定义页面域名绑定应用服务接口
 */
public interface ICustomPageDomainApplicationService {

    /**
     * 根据project_id查询域名列表
     */
    List<CustomPageDomainModel> listByProjectId(Long projectId);

    /**
     * 根据ID查询域名绑定
     */
    CustomPageDomainModel getById(Long id);

    /**
     * 创建域名绑定
     */
    ReqResult<CustomPageDomainModel> create(CustomPageDomainModel model, UserContext userContext);

    /**
     * 更新域名绑定
     */
    ReqResult<CustomPageDomainModel> update(CustomPageDomainModel model, UserContext userContext);

    /**
     * 删除域名绑定
     */
    ReqResult<Void> delete(Long id, UserContext userContext);

    List<String> listAllDomains();
}
