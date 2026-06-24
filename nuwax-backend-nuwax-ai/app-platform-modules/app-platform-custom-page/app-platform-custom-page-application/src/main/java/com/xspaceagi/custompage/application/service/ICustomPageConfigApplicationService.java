package com.xspaceagi.custompage.application.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.xspaceagi.custompage.sdk.dto.DataSourceDto;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xspaceagi.custompage.domain.model.CustomPageConfigModel;
import com.xspaceagi.custompage.sdk.dto.PageArgConfig;
import com.xspaceagi.custompage.sdk.dto.ProxyConfig;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;

/**
 * 前端页面配置应用服务
 */
public interface ICustomPageConfigApplicationService {

    /**
     * 创建用户项目
     */
    ReqResult<CustomPageConfigModel> create(CustomPageConfigModel model, UserContext userContext) throws JsonProcessingException;

    /**
     * 上传项目
     */
    ReqResult<Map<String, Object>> uploadProject(CustomPageConfigModel model, MultipartFile file, boolean isInitProject, UserContext userContext) throws Exception;

    /**
     * 创建反向代理项目
     */
    ReqResult<CustomPageConfigModel> createReverseProxyProject(CustomPageConfigModel model, UserContext userContext);

    /**
     * 查询项目文件内容
     */
    ReqResult<Map<String, Object>> queryProjectContent(Long projectId, String proxyPath);

    /**
     * 查询项目历史版本文件内容
     */
    ReqResult<Map<String, Object>> queryProjectContentByVersion(Long projectId, Integer codeVersion, String proxyPath);

    /**
     * 更新反向代理配置
     */
    ReqResult<List<ProxyConfig>> addProxy(Long projectId, ProxyConfig proxyConfig, UserContext userContext);

    /**
     * 编辑反向代理配置
     */
    ReqResult<Void> editProxyConfig(Long projectId, ProxyConfig proxyConfig, UserContext userContext);

    /**
     * 删除反向代理配置
     */
    ReqResult<Void> deleteProxy(Long projectId, String env, String path, UserContext userContext);

    /**
     * 配置页面参数
     */
    ReqResult<Void> savePathArgs(Long projectId, PageArgConfig pageArgConfig, UserContext userContext);

    /**
     * 添加路径配置
     */
    ReqResult<Void> addPath(Long projectId, PageArgConfig pageArgConfig, UserContext userContext);

    /**
     * 编辑路径配置
     */
    ReqResult<Void> editPath(Long projectId, PageArgConfig pageArgConfig, UserContext userContext);

    /**
     * 删除路径配置
     */
    ReqResult<Void> deletePath(Long projectId, String pageUri, UserContext userContext);

    /**
     * 批量配置反向代理
     */
    ReqResult<Void> batchConfigProxy(Long projectId, List<ProxyConfig> proxyConfigs, UserContext userContext);

    /**
     * 导出项目,已发布的版本
     */
    ReqResult<InputStream> exportProjectPublished(Long projectId, UserContext userContext);

    /**
     * 导出项目,最新的版本
     */
    ReqResult<InputStream> exportProjectLatest(Long projectId, UserContext userContext);

    /**
     * 绑定数据源
     */
    ReqResult<Void> bindDataSource(Long projectId, String type, Long dataSourceId, UserContext userContext);

    /**
     * 解绑数据源
     */
    ReqResult<Void> unbindDataSource(Long projectId, String type, Long dataSourceId, UserContext userContext);

    /**
     * 查询项目
     */
    CustomPageConfigModel getByProjectId(Long projectId);

    /**
     * 批量查询项目
     */
    List<CustomPageConfigModel> listByIds(List<Long> ids);

    /**
     * 修改项目
     */
    ReqResult<CustomPageConfigModel> updateProject(CustomPageConfigModel model, UserContext userContext);

    /**
     * 删除项目
     */
    ReqResult<Map<String, Object>> deleteProject(Long projectId, UserContext userContext);

    /**
     * 复制项目数据源
     * 返回新增(复制)的数据源
     */
    List<DataSourceDto> copyProjectDataSources(CustomPageConfigModel sourceConfig, CustomPageConfigModel targetConfig, UserContext userContext);

}