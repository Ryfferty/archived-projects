package com.xspaceagi.custompage.domain.repository;

import java.util.List;

import com.xspaceagi.custompage.domain.model.CustomPageBuildModel;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.page.SuperPage;

public interface ICustomPageBuildRepository {

        /**
         * 根据项目ID查询记录
         */
        CustomPageBuildModel getByProjectId(Long projectId);

        /**
         * 心跳更新
         */
        void updateKeepAlive(CustomPageBuildModel model, UserContext userContext);

        /**
         * 构建成功后的状态落库
         */
        void updateBuildStatus(Long projectId, Integer codeVersion, UserContext userContext);

        /**
         * 停止开发服务器后的状态落库
         */
        void updateStopDevStatus(Long projectId, UserContext userContext);

        /**
         * 新增项目记录
         */
        Long add(CustomPageBuildModel model, UserContext userContext);

        /**
         * 分页查询
         */
        SuperPage<CustomPageBuildModel> pageQuery(CustomPageBuildModel model, Long current,
                        Long pageSize);

        /**
         * 查询列表
         */
        List<CustomPageBuildModel> list(CustomPageBuildModel model);

        /**
         * 根据项目ID列表查询构建信息列表
         */
        List<CustomPageBuildModel> listByProjectIds(List<Long> projectIdList);

        /**
         * 删除项目
         */
        void deleteByProjectId(Long projectId, UserContext userContext);

        /**
         * 按开发运行状态查询列表
         */
        List<CustomPageBuildModel> listByDevRunning(Integer devRunning);

        /**
         * 更新版本信息
         */
        void updateVersionInfo(CustomPageBuildModel updateModel, UserContext userContext);

}