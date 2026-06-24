package com.xspaceagi.agent.core.adapter.dto;

import lombok.Data;

/**
 * 技能导出结果
 */
@Data
public class SkillExportResultDto {

    /**
     * 导出文件名，包含后缀
     */
    private String fileName;

    /**
     * 文件字节内容
     */
    private byte[] data;

    /**
     * 文件类型
     */
    private String contentType;
}

