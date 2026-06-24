package com.xspaceagi.agent.core.adapter.dto.config.bind;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SkillBindConfigDto implements Serializable {

    @Schema(description = "调用方式,按需调用（ON_DEMAND）、手动选择(MANUAL_ON_DEMAND)")
    private SkillInvokeTypeEnum invokeType;

    @Schema(description = "是否默认选中，0-否，1-是")
    private Integer defaultSelected;

    @Schema(description = "别名")
    private String alias;

    public enum SkillInvokeTypeEnum {
        //按需调用、手动选择，MANUAL 未使用
        ON_DEMAND, MANUAL, MANUAL_ON_DEMAND
    }
}
