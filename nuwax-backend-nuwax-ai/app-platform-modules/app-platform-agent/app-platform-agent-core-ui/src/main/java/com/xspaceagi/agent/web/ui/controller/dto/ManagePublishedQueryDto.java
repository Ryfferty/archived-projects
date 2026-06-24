package com.xspaceagi.agent.web.ui.controller.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagePublishedQueryDto implements Serializable {

    @Schema(description = "目标类型，Agent,Plugin,Workflow")
    private Published.TargetType targetType;

    @Schema(description = "发布子类型类型：PageApp 和 Agent")
    private Published.TargetSubType targetSubType;

    @Schema(description = "关键字搜索")
    private String kw;
}
