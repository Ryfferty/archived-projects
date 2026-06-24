package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("notify_message_user")
public class NotifyMessageUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "_tenant_id")
    private Long tenantId;
    private Long notifyId;
    private Long userId;
    private ReadStatus readStatus;
    private Date modified;
    private Date created;

    public enum ReadStatus {
        Unread,
        Read
    }
}
