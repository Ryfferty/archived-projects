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
@TableName("agent_statistics")
public class AgentStatistics {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "_tenant_id")
    private Long tenantId; // 商户ID

    private Long agentId; // 智能体ID
    private Long userCount; // 用户人数
    private Integer convCount; // 会话次数
    private Integer collectCount; // 收藏次数
    private Integer likeCount; // 点赞次数
    private Integer dislikeCount; // 踩次数
    private Date modified; // 更新时间
    private Date created; // 创建时间
}
