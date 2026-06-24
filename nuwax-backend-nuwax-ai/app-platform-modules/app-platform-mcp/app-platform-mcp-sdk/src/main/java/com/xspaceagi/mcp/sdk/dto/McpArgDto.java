package com.xspaceagi.mcp.sdk.dto;

import com.xspaceagi.mcp.sdk.enums.McpDataTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class McpArgDto {

    @Schema(description = "参数key，唯一标识，不需要前端传递，后台根据配置自动生成")
    private String key;

    @Schema(description = "参数名称，符合函数命名规则")
    private String name;

    @Schema(description = "参数详细描述信息")
    private String description;

    @Schema(description = "数据类型")
    private McpDataTypeEnum dataType;

    @Schema(description = "是否必须")
    private boolean require;

    @Schema(description = "下级参数")
    private List<McpArgDto> subArgs;

    @Schema(description = "绑定值，默认值")
    private String bindValue;

    @Schema(description = "可选枚举范围")
    private List<String> enums;
}
