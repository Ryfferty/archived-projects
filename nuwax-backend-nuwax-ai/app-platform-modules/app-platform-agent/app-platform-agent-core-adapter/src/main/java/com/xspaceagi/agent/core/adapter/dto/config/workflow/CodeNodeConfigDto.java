package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import com.xspaceagi.agent.core.spec.enums.CodeLanguageEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 插件节点配置
 */
@Getter
@Setter
public class CodeNodeConfigDto extends NodeConfigDto {

    @Schema(description = "代码语言")
    private CodeLanguageEnum codeLanguage;

    @Schema(description = "代码JavaScript")
    private String codeJavaScript;

    @Schema(description = "代码Python")
    private String codePython;

}
