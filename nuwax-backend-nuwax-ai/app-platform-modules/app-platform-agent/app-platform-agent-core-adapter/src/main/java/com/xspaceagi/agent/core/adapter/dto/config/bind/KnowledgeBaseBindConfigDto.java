package com.xspaceagi.agent.core.adapter.dto.config.bind;

import com.xspaceagi.agent.core.spec.enums.SearchStrategyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeBaseBindConfigDto implements Serializable {

    @Schema(description = "调用方式")
    private InvokeTypeEnum invokeType;

    @Schema(description = "是否默认选中，0-否，1-是")
    private Integer defaultSelected;

    //搜索策略
    @Schema(description = "搜索策略")
    private SearchStrategyEnum searchStrategy;

    //最大召回数量
    @Schema(description = "最大召回数量")
    private Integer maxRecallCount;

    //匹配度
    @Schema(description = "匹配度,0.01 - 0.99")
    private Double matchingDegree;

    //无匹配回复类型
    @Schema(description = "无召回回复类型，默认、自定义")
    private NoneRecallReplyTypeEnum noneRecallReplyType;

    //无匹配回复
    @Schema(description = "无召回回复自定义内容")
    private String noneRecallReply;

    public enum NoneRecallReplyTypeEnum {
        //自定义回复、默认
        DEFAULT, CUSTOM
    }

    public enum InvokeTypeEnum {
        //自动调用、按需调用
        AUTO, ON_DEMAND, MANUAL, MANUAL_ON_DEMAND
    }
}
