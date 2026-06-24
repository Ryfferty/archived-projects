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

@TableName("agent_temp_chat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentTempChat {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    @TableField(value = "_tenant_id")
    private Long tenantId; // 商户ID
    private Long agentId;
    private String chatKey;
    private Integer requireLogin;
    private Date expire;
    private Date modified;
    private Date created;
}
