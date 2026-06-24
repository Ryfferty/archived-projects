package com.xspaceagi.agent.core.sdk.dto;

import com.xspaceagi.agent.core.sdk.enums.TargetTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class TemplateEnableOrUpdateDto implements Serializable {

    @Schema(description = "操作的用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "模板对应的类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private TargetTypeEnum targetType;

    @Schema(description = "空间ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long spaceId;

    @Schema(description = "模板对应的工作流或智能体ID, 可选，只有更新时需要传")
    private Long targetId;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "配置信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String config;

    @Schema(description = "分类")
    private String category;
}
