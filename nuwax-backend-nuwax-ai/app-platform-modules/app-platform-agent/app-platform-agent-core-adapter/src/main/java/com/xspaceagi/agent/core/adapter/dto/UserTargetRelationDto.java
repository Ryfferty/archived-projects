package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserTargetRelationDto implements Serializable {

    private Long id; // 关系ID

    private Long userId; // 用户ID

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "描述")
    private String description;

    private Date modified; // 更新时间

    private Date created; // 创建时间
}
