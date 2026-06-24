package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CardBindDto implements Serializable {

    @Schema(description = "卡片ID")
    private Long cardId;

    @Schema(description = "卡片唯一标识")
    private String cardKey;

    @Schema(description = "卡片参数绑定信息")
    private List<CardArgsBindConfigDto> cardArgsBindConfigs;

}
