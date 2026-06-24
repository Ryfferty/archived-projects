package com.xspaceagi.agent.core.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 容器操作结果 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComputerPodResultDto {

    /**
     * 状态码，0000 表示成功
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回数据（包含 container_info 等信息）
     */
    private Map<String, Object> data;

    /**
     * 跟踪唯一标识
     */
    private String tid;
}

