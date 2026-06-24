package com.xspaceagi.agent.core.domain.service;

import com.xspaceagi.agent.core.adapter.repository.entity.WorkflowConfig;
import com.xspaceagi.agent.core.adapter.repository.entity.WorkflowNodeConfig;

import java.util.List;
import java.util.Map;

/**
 * 工作流领域服务，包含配置、发布、统计数据相关接口
 */
public interface WorkflowDomainService {

    /**
     * 新增工作流
     *
     * @param workflowConfig
     */
    void add(WorkflowConfig workflowConfig);

    /**
     * 删除工作流
     *
     * @param workflowId
     */
    void delete(Long workflowId);

    /**
     * 根据空间ID删除工作流
     *
     * @param spaceId
     */
    void deleteBySpaceId(Long spaceId);

    /**
     * 更新工作流
     *
     * @param workflowConfig
     */
    void update(WorkflowConfig workflowConfig);

    WorkflowConfig queryById(Long workflowId);

    List<WorkflowConfig> queryListByIds(List<Long> workflowIds);

    /**
     * 根据空间ID查询工作流列表
     */
    List<WorkflowConfig> queryListBySpaceId(Long spaceId);

    /**
     * 工作空间移动
     */
    void transfer(Long workflowId, Long targetSpaceId);

    /**
     * 复制工作流配置
     *
     * @param userId
     * @param workflowId
     * @return
     */
    Long copy(Long userId, Long workflowId);

    Long copy(Long userId, WorkflowConfig workflowConfig, List<WorkflowNodeConfig> workflowNodeConfigs, Long targetSpaceId);

    List<WorkflowNodeConfig> queryNodeConfigListByWorkflowId(Long workflowId);


    /**
     * 添加节点
     */
    void addWorkflowNode(WorkflowNodeConfig workflowNodeConfig);

    /**
     * 更新节点配置
     */
    void updateWorkflowNodeConfig(WorkflowNodeConfig workflowNodeConfig);

    /**
     * 删除节点
     */
    void deleteWorkflowNode(Long id);

    /**
     * 获取节点配置
     *
     * @param id
     * @return
     */
    WorkflowNodeConfig queryWorkflowNode(Long id);

    /**
     * 检查并更新循环节点的开始和结束节点
     *
     * @param workflowNodeConfig
     * @param workflowNodeConfigMap
     * @return
     */
    boolean checkAndUpdateLoopStartAndEndNodes(WorkflowNodeConfig workflowNodeConfig, Map<Long, WorkflowNodeConfig> workflowNodeConfigMap);

    void restoreWorkflow(WorkflowConfig workflowConfig, List<WorkflowNodeConfig> workflowNodeConfigs);
}
