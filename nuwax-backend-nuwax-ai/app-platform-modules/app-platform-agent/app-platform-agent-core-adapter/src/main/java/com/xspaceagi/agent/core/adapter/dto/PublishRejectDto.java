package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublishRejectDto implements Serializable {

    @Schema(description = "发布申请ID", hidden = true)
    private Long applyId;

    @Schema(description = "拒绝原因")
    private String reason;
}
