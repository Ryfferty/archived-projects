package com.xspaceagi.agent.core.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("workflow_config_history")
public class WorkflowConfigHistory {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("_tenant_id")
    private Long tenantId;

    @TableField("op_user_id")
    private Long opUserId;

    @TableField("workflow_id")
    private Long workflowId;

    private Type type;

    private String config;

    private String description;

    private Date modified;

    private Date created;

    public enum Type {
        Add, Edit, Publish, PublishApply, PublishApplyReject, OffShelf, AddNode, EditNode, DeleteNode
    }
}
