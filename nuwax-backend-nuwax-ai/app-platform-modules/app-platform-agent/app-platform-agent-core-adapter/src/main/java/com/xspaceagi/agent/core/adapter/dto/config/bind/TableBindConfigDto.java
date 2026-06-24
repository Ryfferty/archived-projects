package com.xspaceagi.agent.core.adapter.dto.config.bind;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TableBindConfigDto implements Serializable {

    @Schema(description = "入参绑定配置，插件、工作流有效")
    private List<Arg> inputArgBindConfigs;

    @Schema(description = "参数绑定信息")
    private List<Arg> outputArgBindConfigs;

    @Schema(description = "卡片绑定信息")
    private CardBindConfigDto cardBindConfig;
}
