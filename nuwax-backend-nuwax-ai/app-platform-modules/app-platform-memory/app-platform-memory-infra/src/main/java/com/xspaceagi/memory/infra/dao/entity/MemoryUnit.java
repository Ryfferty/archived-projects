package com.xspaceagi.memory.infra.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 记忆单元实体
 * @TableName memory_unit
 */
@TableName(value = "memory_unit")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoryUnit {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    @TableField(value = "_tenant_id")
    private Long tenantId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 代理ID
     */
    private Long agentId;

    /**
     * 一级分类
     */
    private String category;

    /**
     * 二级分类
     */
    private String subCategory;

    /**
     * 内容JSON
     */
    private String contentJson;

    /**
     * 是否敏感信息(0:否 1:是)
     */
    private Boolean isSensitive;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;
}
