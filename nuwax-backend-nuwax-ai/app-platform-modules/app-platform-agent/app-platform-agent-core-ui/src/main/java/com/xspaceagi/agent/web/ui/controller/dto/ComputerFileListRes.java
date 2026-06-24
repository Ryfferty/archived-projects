package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询文件列表响应")
public class ComputerFileListRes {

    @Schema(description = "文件列表")
    private Object files;

}
