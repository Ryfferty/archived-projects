package com.xspaceagi.sandbox.domain.model;

import com.xspaceagi.sandbox.spec.enums.SandboxScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 沙盒配置领域模型
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SandboxConfigModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    private Long id;

    /**
     * 配置范围：global-全局配置 user-个人配置
     */
    private SandboxScopeEnum scope;

    /**
     * 用户ID（scope为user时必填）
     */
    private Long userId;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 唯一标识，用户智能体电脑连接有用
     */
    private String configKey;

    /**
     * 配置值（JSON格式字符串）
     */
    private String configValue;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 是否启用：true-启用 false-禁用
     */
    private Boolean isActive;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date modified;
}
