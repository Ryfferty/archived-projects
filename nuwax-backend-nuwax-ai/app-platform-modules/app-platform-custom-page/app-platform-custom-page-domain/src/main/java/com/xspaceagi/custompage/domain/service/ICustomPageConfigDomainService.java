package com.xspaceagi.custompage.domain.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.xspaceagi.custompage.domain.model.CustomPageConfigModel;
import com.xspaceagi.custompage.sdk.dto.DataSourceDto;
import com.xspaceagi.custompage.sdk.dto.ExportTypeEnum;
import com.xspaceagi.custompage.sdk.dto.PageArgConfig;
import com.xspaceagi.custompage.sdk.dto.ProxyConfig;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.spec.page.SuperPage;

/**
 * 自定义页面配置领域服务接口
 */
public interface ICustomPageConfigDomainService {

    /**
     * 创建自定义页面配置
     */
    ReqResult<CustomPageConfigModel> create(CustomPageConfigModel model, UserContext userContext);

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
     * 根据项目ID列表查询配置
     */
    List<CustomPageConfigModel> listByIds(List<Long> ids);

    /**
     * 更新
     */
    ReqResult<CustomPageConfigModel> update(CustomPageConfigModel model, UserContext userContext);

    /**
     * 添加反向代理配置
     */
    ReqResult<List<ProxyConfig>> addProxy(Long projectId, ProxyConfig proxyConfig, UserContext userContext);

    /**
     * 编辑反向代理配置
     */
    ReqResult<Void> editProxy(Long projectId, ProxyConfig proxyConfig, UserContext userContext);

    /**
     * 删除反向代理配置
     */
    ReqResult<Void> deleteProxy(Long projectId, String env, String path, UserContext userContext);

    /**
     * 配置路径参数
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
     * 导出项目
     */
    ReqResult<InputStream> exportProject(Long projectId, ExportTypeEnum exportType, UserContext userContext);

    /**
     * 查询项目文件内容
     */
    ReqResult<Map<String, Object>> queryProjectContent(Long projectId, String command, String proxyPath);

    /**
     * 查询项目历史版本文件内容
     */
    ReqResult<Map<String, Object>> queryProjectContentByVersion(Long projectId, Integer codeVersion, String proxyPath);

    /**
     * 绑定数据源
     */
    ReqResult<Void> bindDataSource(Long projectId, DataSourceDto dataSource, UserContext userContext);

    /**
     * 解绑数据源
     */
    ReqResult<Void> unbindDataSource(Long projectId, DataSourceDto dataSource, UserContext userContext);

    /**
     * 删除项目
     */
    ReqResult<Map<String, Object>> delete(Long projectId, UserContext userContext);

    /**
     * 导入项目配置
     */
    void importProjectConfig(CustomPageConfigModel model, UserContext userContext);

    /**
     * 根据devAgentId列表查询配置
     */
    List<CustomPageConfigModel> listByDevAgentIds(List<Long> devAgentIds);

    /**
     * 统计网页应用总数
     */
    Long countTotalPages();

}
