package com.xspaceagi.agent.core.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.agent.core.spec.enums.NodeTypeEnum;
import lombok.Data;

@TableName("agent_node")
@Data
public class AgentNode {
    @TableId(value = "node_id", type = IdType.AUTO)
    private Long nodeId;

    private String name;

    private Long agentId;

    private Long targetId;

    private NodeTypeEnum nodeType;

    private String toolConfig;

    private String bindArgs;

    private String textArg;

    private Integer exceptionOut;

    private Long sort;
}
