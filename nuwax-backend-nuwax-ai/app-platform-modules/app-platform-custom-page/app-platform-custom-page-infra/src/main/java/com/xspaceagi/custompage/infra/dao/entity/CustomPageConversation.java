package com.xspaceagi.custompage.infra.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName(value = "custom_page_conversation")
@Data
public class CustomPageConversation {

    private Long id;

    // 项目ID
    private Long projectId;

    // 会话主题
    private String topic;

    // 会话内容
    private String content;
    
    // 消息角色：USER/ASSISTANT
    private String role;

    // 会话ID
    private String sessionId;

    // 请求ID
    private String requestId;

    @TableField(value = "_tenant_id")
    private Long tenantId;

    private Long spaceId;

    private Date created;
    private Long creatorId;
    private String creatorName;
    private Date modified;
    private Long modifiedId;
    private String modifiedName;

    private Integer yn;
}
