package com.xspaceagi.agent.core.adapter.dto.config.bind;

import com.xspaceagi.agent.core.adapter.dto.CardArgsBindConfigDto;
import com.xspaceagi.agent.core.spec.enums.BindCardStyleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CardBindConfigDto implements Serializable {

    @Schema(description = "卡片ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long cardId;

    @Schema(description = "卡片唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cardKey;

    @Schema(description = "卡片绑定样式")
    private BindCardStyleEnum bindCardStyle;

    @Schema(description = "最大卡片列表数量")
    private Integer maxCardCount;

    @Schema(description = "卡片数组绑定信息")
    private String bindArray;

    @Schema(description = "卡片参数绑定信息")
    private List<CardArgsBindConfigDto> cardArgsBindConfigs;

    @Schema(description = "绑定跳转链接地址")
    private String bindLinkUrl;
}
