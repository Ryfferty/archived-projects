package com.xspaceagi.agent.web.ui.dto;

import com.xspaceagi.custompage.sdk.dto.CopyTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "技能复制请求DTO")
public class SkillCopyDto implements Serializable {

    @NonNull
    @Schema(description = "技能ID")
    private Long skillId;

    @Schema(description = "目标空间ID")
    private Long targetSpaceId;

    @Schema(description = "目标空间类型")
    private CopyTypeEnum copyType;
}