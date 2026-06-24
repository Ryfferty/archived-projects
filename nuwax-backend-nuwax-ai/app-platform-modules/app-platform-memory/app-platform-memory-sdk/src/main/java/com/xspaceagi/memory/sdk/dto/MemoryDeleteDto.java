package com.xspaceagi.memory.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class MemoryDeleteDto {

    @Schema(description = "记忆ID列表")
    private List<Long> memoryIds;
}
