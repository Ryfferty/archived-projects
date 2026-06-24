package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublishChannelDto implements Serializable {

    @Schema(description = "发布渠道")
    private Published.PublishChannel channel;
}
