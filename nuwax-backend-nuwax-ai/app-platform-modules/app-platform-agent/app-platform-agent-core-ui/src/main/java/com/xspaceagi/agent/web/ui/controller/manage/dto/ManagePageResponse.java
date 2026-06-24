package com.xspaceagi.agent.web.ui.controller.manage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理分页响应")
public class ManagePageResponse<T> {

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "当前页码")
    private Integer pageNo;

    @Schema(description = "每页大小")
    private Integer pageSize;

    @Schema(description = "数据列表")
    private List<T> records;
}