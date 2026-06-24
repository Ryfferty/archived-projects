package com.xspaceagi.agent.core.domain.service;

import com.xspaceagi.agent.core.adapter.dto.ComputerPodResultDto;

/**
 * 容器领域服务
 */
public interface ComputerPodDomainService {

    /**
     * 启动容器
     */
    ComputerPodResultDto ensurePod(Long cId, Long userId);

    /**
     * 容器保活
     */
    ComputerPodResultDto keepalive(Long cId, Long userId);

    /**
     * 重启容器（销毁后重建）
     */
    ComputerPodResultDto restart(Long cId, Long userId);

    /**
     * 查询容器 VNC 服务状态
     */
    ComputerPodResultDto getVncStatus(Long cId, Long userId);
}


