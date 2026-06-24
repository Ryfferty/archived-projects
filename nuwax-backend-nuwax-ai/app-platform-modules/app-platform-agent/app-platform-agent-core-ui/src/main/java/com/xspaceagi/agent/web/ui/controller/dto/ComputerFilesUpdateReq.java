package com.xspaceagi.agent.web.ui.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xspaceagi.agent.core.adapter.dto.ComputerFileInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "用户文件更新请求")
public class ComputerFilesUpdateReq {

    @NotNull
    @JsonProperty("cId")
    @Schema(description = "会话ID")
    private Long cId;

    @NotNull
    @Schema(description = "文件列表")
    private List<ComputerFileInfo> files;

}
