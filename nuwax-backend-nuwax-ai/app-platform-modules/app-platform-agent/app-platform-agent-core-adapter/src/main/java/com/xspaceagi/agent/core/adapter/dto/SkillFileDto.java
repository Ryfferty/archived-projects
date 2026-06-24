package com.xspaceagi.agent.core.adapter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SkillFileDto {

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件内容")
    private String contents;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "文件代理下载地址")
    private String fileProxyUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "重命名前的文件名")
    private String renameFrom;

    //create | delete | rename | modify
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "操作类型")
    private String operation;

    @Schema(description = "是否目录")
    private Boolean isDir = false;
}