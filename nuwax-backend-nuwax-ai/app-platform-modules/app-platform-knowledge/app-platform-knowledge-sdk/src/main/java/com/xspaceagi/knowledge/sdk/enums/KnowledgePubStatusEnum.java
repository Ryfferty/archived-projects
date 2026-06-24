package com.xspaceagi.knowledge.sdk.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "知识状态")
@Getter
public enum KnowledgePubStatusEnum {
    Waiting,
    Published
}
