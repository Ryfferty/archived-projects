package com.xspaceagi.agent.core.sdk.dto;

import com.xspaceagi.agent.core.spec.enums.DataTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArgDto implements Serializable {
    @Schema(description = "参数key，唯一标识，不需要前端传递，后台根据配置自动生成")
    private String key;

    @Schema(description = "参数名称，符合函数命名规则")
    private String name;

    @Schema(description = "参数详细描述信息")
    private String description;

    @Schema(description = "数据类型")
    private DataTypeEnum dataType;

    @Schema(description = "是否必须")
    private boolean require;

    @Schema(description = "是否启用")
    private Boolean enable;

    @Schema(description = "下级参数")
    private List<ArgDto> subArgs;
}
