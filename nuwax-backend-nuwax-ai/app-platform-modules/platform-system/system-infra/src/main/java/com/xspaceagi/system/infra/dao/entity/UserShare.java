package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.system.sdk.service.dto.UserShareDto;
import com.xspaceagi.system.spec.common.JsonTypeHandler;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user_share", autoResultMap = true)
public class UserShare {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String shareKey;
    @TableField(value = "_tenant_id")
    private Long tenantId;
    private Long userId;
    private UserShareDto.UserShareType type;
    private String targetId;

    @TableField(value = "content", typeHandler = JsonTypeHandler.class)
    private Object content;
    //有效期
    private Date expire;
    private Date modified;
    private Date created;
}
