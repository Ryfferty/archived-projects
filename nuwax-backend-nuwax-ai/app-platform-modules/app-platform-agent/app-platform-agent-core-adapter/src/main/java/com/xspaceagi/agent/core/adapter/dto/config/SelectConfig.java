package com.xspaceagi.agent.core.adapter.dto.config;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SelectConfig {

    @Schema(description = "数据源类型")
    private DataSourceTypeEnum dataSourceType;

    @Schema(description = "数据源类型")
    private Published.TargetType targetType;

    @Schema(description = "插件或工作流ID，dataSource选择PLUGIN时有用")
    private Long targetId;

    @Schema(description = "插件或工作流名称")
    private String targetName;

    @Schema(description = "插件或工作流描述")
    private String targetDescription;

    @Schema(description = "插件或工作流图标")
    private String targetIcon;

    @Schema(description = "下拉选项配置")
    private List<SelectOption> options;

    @Data
    public static class SelectOption {
        private String label;
        private String value;
        private List<SelectOption> children;
    }

    public enum DataSourceTypeEnum {
        //手动创建
        MANUAL,
        //数据绑定
        BINDING
    }

}
