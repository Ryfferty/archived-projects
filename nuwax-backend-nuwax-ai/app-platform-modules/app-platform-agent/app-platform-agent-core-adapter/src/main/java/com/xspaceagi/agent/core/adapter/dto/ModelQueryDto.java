package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.ModelConfig;
import com.xspaceagi.agent.core.spec.enums.ModelApiProtocolEnum;
import com.xspaceagi.agent.core.spec.enums.ModelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ModelQueryDto {

    @Schema(description = "模型类型")
    private ModelTypeEnum modelType;

    @Schema(description = "接口协议类型")
    private ModelApiProtocolEnum apiProtocol;

    @Schema(description = "模型范围，不传则返回所有有权限的模型")
    private ModelConfig.ModelScopeEnum scope;

    @Schema(description = "空间ID，可选，传递后会返回当前空间管理的模型")
    private Long spaceId;

    @Schema(description = "是否启用")
    private Integer enabled;
}
