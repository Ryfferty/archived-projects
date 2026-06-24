package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class OffShelfDto implements Serializable {

    @Schema(description = "发布ID", hidden = true)
    private Long publishId;

    @Schema(description = "下架原因")
    private String reason;
}
