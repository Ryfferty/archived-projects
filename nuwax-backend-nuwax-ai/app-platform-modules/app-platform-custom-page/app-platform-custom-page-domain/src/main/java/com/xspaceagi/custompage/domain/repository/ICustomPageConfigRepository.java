package com.xspaceagi.custompage.domain.repository;

import java.util.List;

import com.xspaceagi.custompage.domain.model.CustomPageConfigModel;
import com.xspaceagi.custompage.sdk.dto.PublishTypeEnum;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.page.SuperPage;

/**
 * 自定义页面配置仓储接口
 */
public interface ICustomPageConfigRepository {

    /**
     * 查询配置列表
     */
    List<CustomPageConfigModel> list(CustomPageConfigModel model);

    /**
     * 分页查询配置
     */
    SuperPage<CustomPageConfigModel> pageQuery(CustomPageConfigModel configModel, Long current, Long pageSize);

    /**
     * 根据ID查询配置
     */
    CustomPageConfigModel getById(Long id);

    /**
     * 根据agentId查询配置
     */
    CustomPageConfigModel getByAgentId(Long agentId);

    /**
     * 根据basePath查询配置
     */
    CustomPageConfigModel getByBasePath(String basePath);

    /**
     * 新增配置
     */
    Long add(CustomPageConfigModel model, UserContext userContext);

    /**
     * 更新配置
     */
    void updateById(CustomPageConfigModel model, UserContext userContext);

    /**
     * 更新构建状态
     */
    void updateBuildStatus(Long projectId, PublishTypeEnum publishTypeEnum, UserContext userContext);

    /**
     * 根据ID删除配置
     */
    void deleteById(Long projectId, UserContext userContext);

    /**
     * 根据devAgentId列表查询配置
     */
    List<CustomPageConfigModel> listByDevAgentIds(List<Long> devAgentIds);

    /**
     * 根据id列表查询配置
     */
    List<CustomPageConfigModel> listByIds(List<Long> ids);

    /**
     * 统计网页应用总数
     *
     * @return 网页应用总数
     */
    Long countTotalPages();

}
