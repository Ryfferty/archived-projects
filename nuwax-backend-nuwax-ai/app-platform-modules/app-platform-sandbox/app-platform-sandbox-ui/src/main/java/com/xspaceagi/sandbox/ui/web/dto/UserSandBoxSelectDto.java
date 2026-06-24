package com.xspaceagi.sandbox.ui.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserSandBoxSelectDto {

    @Schema(description = "可选的沙盒列表")
    private List<SelectDto> sandboxes;

    @Schema(description = "已选择的沙盒, key为agentId，value为sandboxId")
    private Map<String, String> agentSelected;

    @Data
    public static class SelectDto {
        private String sandboxId;
        private String name;
        private String description;
    }
}
