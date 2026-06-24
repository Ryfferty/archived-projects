package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComputerFileInfo {

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "是否为二进制文件")
    private boolean binary;

    @Schema(description = "文件大小是否超过限制")
    private boolean sizeExceeded;

    @Schema(description = "文件内容")
    private String contents;

    @Schema(description = "重命名前的文件名")
    private String renameFrom;

    //create | delete | rename | modify
    @Schema(description = "操作类型")
    private String operation;

    @Schema(description = "是否目录")
    private Boolean isDir = false;
}