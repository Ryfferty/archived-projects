package com.xspaceagi.agent.core.adapter.application;

import com.xspaceagi.agent.core.adapter.dto.ComputerPodResultDto;

/**
 * 容器应用服务
 */
public interface ComputerPodApplicationService {

    /**
     * 启动容器
     */
    ComputerPodResultDto ensurePod(Long cId);

    /**
     * 容器保活
     */
    ComputerPodResultDto keepalive(Long cId);

    /**
     * 重启容器（销毁后重建）
     */
    ComputerPodResultDto restart(Long cId);

    /**
     * 查询容器 VNC 服务状态
     */
    ComputerPodResultDto getVncStatus(Long cId);
}
