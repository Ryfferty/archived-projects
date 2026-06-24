package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TicketCreateDto {

    @Schema(description = "认证Token")
    private String token;
}
