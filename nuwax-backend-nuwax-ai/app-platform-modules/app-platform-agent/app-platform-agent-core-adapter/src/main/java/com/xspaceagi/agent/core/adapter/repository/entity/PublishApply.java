package com.xspaceagi.agent.core.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.system.spec.common.JsonTypeHandlerWithoutType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName(value = "publish_apply", autoResultMap = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishApply {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long spaceId;

    @TableField("_tenant_id")
    private Long tenantId;

    @TableField("apply_user_id")
    private Long applyUserId;

    @TableField("target_type")
    private Published.TargetType targetType;

    @TableField("target_sub_type")
    private Published.TargetSubType targetSubType;

    @TableField("target_id")
    private Long targetId;

    private String name;

    private String description;

    private String icon;

    private String config;

    @TableField(value = "ext", typeHandler = JsonTypeHandlerWithoutType.class)
    private Object ext;

    private String remark;

    @TableField(value = "channel")
    private String channels;

    private Published.PublishScope scope;

    @TableField("publish_status")
    private Published.PublishStatus publishStatus;

    private Date modified;

    private Date created;

    private String category;

    private Integer allowCopy;

    private Integer onlyTemplate;
}