package com.xspaceagi.agent.core.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("config_history")
public class ConfigHistory {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "_tenant_id")
    private Long tenantId;
    //op_user_id
    private Long opUserId;
    private Published.TargetType targetType;
    private Long targetId;
    private Type type;
    private String config;
    private String description;
    private Date modified;
    private Date created;

    public enum Type {
        Add, Edit, Publish, PublishApply, PublishApplyReject, OffShelf, AddComponent, EditComponent, DeleteComponent, AddNode, EditNode, DeleteNode
    }
}