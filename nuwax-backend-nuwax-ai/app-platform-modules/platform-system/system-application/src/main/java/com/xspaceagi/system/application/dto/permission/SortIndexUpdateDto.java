package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 顺序批量调整请求
 */
@Data
public class SortIndexUpdateDto implements Serializable {

    @Schema(description = "待调整的列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SortIndexDto> items;
}
