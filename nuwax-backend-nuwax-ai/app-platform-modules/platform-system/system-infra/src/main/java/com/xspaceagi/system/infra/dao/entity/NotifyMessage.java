package com.xspaceagi.system.infra.dao.entity;

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
@TableName("notify_message")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyMessage {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "_tenant_id")
    private Long tenantId;
    private Long senderId;
    private MessageScope scope;
    private String content;
    private Date modified;
    private Date created;

    //消息范围 Broadcast 广播消息；Private 私对私消息
    public enum MessageScope {
        Broadcast,
        Private,

        System,
    }
}
