package com.xspaceagi.system.sdk.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "token限制")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenLimit implements Serializable {

    @Schema(description = "每日token限制数量，-1表示不限制")
    private Long limitPerDay;

}