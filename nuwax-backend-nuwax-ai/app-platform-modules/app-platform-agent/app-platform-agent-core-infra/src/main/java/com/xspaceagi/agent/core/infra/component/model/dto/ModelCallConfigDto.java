package com.xspaceagi.agent.core.infra.component.model.dto;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.spec.enums.OutputTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModelCallConfigDto implements Serializable {

    @Schema(description = "生成随机性;0-1")
    private Double temperature;

    @Schema(description = "累计概率: 模型在生成输出时会从概率最高的词汇开始选择;0-1")
    private Double topP;

    @Schema(description = "token上限")
    private Integer maxTokens; // token上限

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "用户提示词")
    private String userPrompt;

    private int chatRound;

    @Schema(description = "是否流式输出")
    private boolean streamCall;

    @Schema(description = "组件配置列表")
    private List<ComponentConfig> componentConfigs;

    @Schema(description = "输出类型，文本 Text；Markdown；JSON")
    private OutputTypeEnum outputType;

    private String jsonSchema;

    @Schema(description = "outputType为JSON时的输出参数")
    private List<Arg> outputArgs;

}
