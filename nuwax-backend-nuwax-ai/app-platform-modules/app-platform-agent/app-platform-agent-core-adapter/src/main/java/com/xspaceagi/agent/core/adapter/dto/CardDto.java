package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CardDto {

    @Schema(description = "卡片ID")
    private Long id; // 卡片ID

    @Schema(description = "卡片唯一标识")
    private String cardKey; // 卡片唯一标识，与前端组件做关联

    @Schema(description = "卡片名称")
    private String name; // 卡片名称

    @Schema(description = "卡片示例图片地址")
    private String imageUrl; // 卡片示例图片地址

    @Schema(description = "卡片参数")
    private List<CardArgsDto> argList; // 卡片参数

    @Data
    public static class CardArgsDto {
        @Schema(description = "卡片参数名称key")
        private String key;

        @Schema(description = "卡片参数引用绑定提示信息")
        private String placeholder;
    }
}
