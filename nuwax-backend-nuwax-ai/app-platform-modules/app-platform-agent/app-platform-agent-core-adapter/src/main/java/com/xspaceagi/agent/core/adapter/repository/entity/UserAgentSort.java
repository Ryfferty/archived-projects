package com.xspaceagi.agent.core.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.agent.core.spec.handler.JsonTypeHandler;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "user_agent_sort", autoResultMap = true)
public class UserAgentSort {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("_tenant_id")
    private Long tenantId;
    private Long userId;
    private String category;
    private Integer sort;
    @TableField(value = "agent_sort_config", typeHandler = JsonTypeHandler.class)
    private List<Long> agentSortConfig;
    private String modified;
    private String created;
}
