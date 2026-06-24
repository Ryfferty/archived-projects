package com.xspaceagi.custompage.application.service;

import java.util.Map;

import com.xspaceagi.custompage.domain.model.CustomPageBuildModel;
import com.xspaceagi.custompage.sdk.dto.TemplateTypeEnum;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;

/**
 * 前端项目构建应用服务
 */
public interface ICustomPageBuildApplicationService {

        /**
         * 启动前端开发服务器
         */
        ReqResult<Map<String, Object>> startDev(Long projectId, UserContext userContext);

        /**
         * 构建并发布前端项目
         */
        ReqResult<Map<String, Object>> build(Long projectId, String publishType, UserContext userContext);

        /**
         * 停止前端开发服务器
         */
        ReqResult<Map<String, Object>> stopDev(Long projectId, UserContext userContext);

        /**
         * 重启前端开发服务器
         */
        ReqResult<Map<String, Object>> restartDev(Long projectId, UserContext userContext);

        /**
         * 保活接口
         */
        ReqResult<Map<String, Object>> keepAlive(Long projectId, UserContext userContext);

        /**
         * 根据项目ID查询构建信息
         */
        CustomPageBuildModel getByProjectId(Long id);

        /**
         * 初始化项目工程
         */
        ReqResult<Map<String, Object>> initProject(Long projectId, TemplateTypeEnum templateType, UserContext userContext);

        /**
         * 删除项目文件
         */
        ReqResult<Map<String, Object>> deleteProjectFiles(CustomPageBuildModel model, UserContext userContext);

        /**
         * 查询开发服务器日志
         */
        ReqResult<Map<String, Object>> getDevLog(Long projectId, Integer startIndex, String logType, UserContext userContext);

        /**
         * 复制项目工程
         */
        ReqResult<Map<String, Object>> copyProject(Long sourceProjectId, Long targetProjectId, UserContext userContext);

        /**
         * 获取日志缓存统计
         */
        ReqResult<Map<String, Object>> getLogCacheStats();

        /**
         * 清理所有日志缓存
         */
        ReqResult<Map<String, Object>> clearAllLogCache();
}