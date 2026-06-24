package com.xspaceagi.system.web.dto.operatorlog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "枚举选项")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumOptionDto {

    @Schema(description = "显示文本")
    private String label;

    @Schema(description = "实际值")
    private String value;
}
