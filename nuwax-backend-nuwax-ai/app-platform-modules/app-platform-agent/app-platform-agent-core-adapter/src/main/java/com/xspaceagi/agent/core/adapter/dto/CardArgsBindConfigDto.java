package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CardArgsBindConfigDto {

    @Schema(description = "卡片参数名称key")
    private String key;

    @Schema(description = "卡片参数引用信息，例如插件的出参 data.xxx")
    private String bindValue;
}
