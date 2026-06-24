package com.xspaceagi.custompage.domain.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xspaceagi.custompage.domain.model.CustomPageBuildModel;
import com.xspaceagi.custompage.sdk.dto.TemplateTypeEnum;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.spec.page.SuperPage;

public interface ICustomPageBuildDomainService {

        /**
         * 根据projectId查询构建信息
         */
        CustomPageBuildModel getByProjectId(Long projectId);

        /**
         * 创建前端页面项目
         */
        ReqResult<CustomPageBuildModel> createProject(Long projectId, Long spaceId,
                        UserContext userContext)
                        throws JsonProcessingException;

        /**
         * 初始化项目工程
         */
        ReqResult<Map<String, Object>> initProject(Long projectId, TemplateTypeEnum templateType);

        /**
         * 上传项目
         */
        ReqResult<Map<String, Object>> uploadProject(Long projectId, MultipartFile file, boolean isInitProject,
                        UserContext userContext);

        /**
         * 启动开发服务器
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
         * 分页查询前端页面项目
         */
        SuperPage<CustomPageBuildModel> pageQuery(CustomPageBuildModel model, Long current,
                        Long pageSize);

        /**
         * 查询前端页面项目列表
         */
        List<CustomPageBuildModel> list(CustomPageBuildModel model);

        /**
         * 根据项目ID列表查询构建信息列表
         */
        List<CustomPageBuildModel> listByProjectIds(List<Long> projectIdList);

        /**
         * 保活处理
         */
        ReqResult<Map<String, Object>> keepAlive(Long projectId, UserContext userContext);

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
        ReqResult<Map<String, Object>> copyProject(Long sourceProjectId, Long targetProjectId);

        /**
         * 获取日志缓存统计
         */
        ReqResult<Map<String, Object>> getLogCacheStats();

        /**
         * 清理所有日志缓存
         */
        ReqResult<Map<String, Object>> clearAllLogCache();
}
