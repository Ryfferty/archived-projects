package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "统计信息(智能体、插件、工作流相关的统计都在该结构里，根据实际情况取值)")
public class StatisticsDto {

    private Long targetId;

    @Schema(description = "用户人数")
    private Long userCount; // 用户人数

    @Schema(description = "会话次数")
    private Long convCount; // 会话次数

    @Schema(description = "收藏次数")
    private Long collectCount; // 收藏次数

    @Schema(description = "点赞次数")
    private Long likeCount; // 点赞次数

    @Schema(description = "引用次数")
    private Long referenceCount;

    @Schema(description = "调用总次数")
    private Long callCount;

    @Schema(description = "失败调用次数")
    private Long failCallCount;

    @Schema(description = "调用总时长")
    private Long totalCallDuration;

}
