package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.adapter.dto.config.ModelConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.bind.ModelBindConfigDto;
import com.xspaceagi.agent.core.spec.enums.OutputTypeEnum;
import com.xspaceagi.agent.core.spec.enums.SearchStrategyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class LLMNodeConfigDto extends NodeConfigDto {

    @Schema(description = "LLM模型ID")
    private Long modelId;

    @Schema(description = "模式：Precision 精确模式；Balanced 平衡模式；Creative 创意模式；Customization 自定义")
    private ModelBindConfigDto.Mode mode;

    @Schema(description = "生成随机性;0-1")
    private Double temperature;

    @Schema(description = "累计概率: 模型在生成输出时会从概率最高的词汇开始选择;0-1")
    private Double topP;

    @Schema(description = "token上限")
    private Integer maxTokens; // token上限

    @Schema(description = "技能")
    private List<SkillComponentConfigDto> skillComponentConfigs;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "用户提示词")
    private String userPrompt;

    @Schema(description = "输出类型，文本 Text；Markdown；JSON")
    private OutputTypeEnum outputType;

    @Schema(hidden = true)
    private ModelConfigDto modelConfig;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkillComponentConfigDto {

        @Schema(description = "技能组件名称")
        private String name;

        @Schema(description = "技能组件图标")
        private String icon;

        @Schema(description = "技能组件描述")
        private String description;

        @Schema(description = "技能组件类型")
        private Type type;

        @Schema(description = "关联的技能组件ID")
        private Long typeId; // 关联的组件ID

        @Schema(description = "入参绑定配置，插件、工作流有效")
        private List<Arg> inputArgBindConfigs;

        @Schema(description = "出参绑定配置，插件、工作流有效")
        private List<Arg> outputArgBindConfigs;

        @Schema(description = "知识库召回策略, Mix混合；Semantics语义；FullText全文")
        private SearchStrategyEnum kbRecallStrategy;

        @Schema(description = "知识库最大召回数量")
        private Integer kbMaxRecallCount;

        @Schema(description = "知识库最小匹配度")
        private Double kbMinMatchScore;

        @Schema(description = "知识库无召回回复")
        private String kbNoRecallReply;

        @Schema(description = "MCP工具名称")
        private String toolName;

        @Schema(hidden = true)
        private Object targetConfig; // 组件原始配置

        public enum Type {
            Plugin, Workflow, Knowledge, Mcp
        }

    }
}
