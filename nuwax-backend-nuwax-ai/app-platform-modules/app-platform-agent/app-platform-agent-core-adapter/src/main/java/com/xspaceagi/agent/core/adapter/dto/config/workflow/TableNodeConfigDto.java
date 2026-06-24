package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class TableNodeConfigDto extends NodeConfigDto {

    @Schema(description = "表格ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long tableId;

    @Schema(description = "数据表名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "数据表描述")
    private String description;

    @Schema(description = "数据表图标")
    private String icon;

    @Schema(description = "数据表字段")
    private List<Arg> tableFields;

    @Schema(description = "数据表原始配置", hidden = true)
    private String originalTableConfig;

    public enum ConditionTypeEnum {
        //AND
        AND,
        //OR
        OR
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConditionArgDto implements Serializable {

        @Schema(description = "比较的第一个入参")
        private Arg firstArg;

        //比较类型
        @Schema(description = "比较类型，等于：EQUAL, 不等于：NOT_EQUAL, 大于：GREATER_THAN, 大于等于：GREATER_THAN_OR_EQUAL, 小于：LESS_THAN, 小于等于：LESS_THAN_OR_EQUAL, 属于：IN, 不属于：NOT_IN, 为空：IS_NULL, 不为空：NOT_NULL")
        private CompareTypeEnum compareType;

        @Schema(description = "比较的第二个入参")
        private Arg secondArg;
    }

    public enum CompareTypeEnum {
        //等于
        EQUAL,
        //不等于
        NOT_EQUAL,
        //大于
        GREATER_THAN,
        //大于等于
        GREATER_THAN_OR_EQUAL,
        //小于
        LESS_THAN,
        //小于等于
        LESS_THAN_OR_EQUAL,
        //属于
        IN,
        //不属于
        NOT_IN,
        //为空
        IS_NULL,
        //不为空
        NOT_NULL
    }
}
