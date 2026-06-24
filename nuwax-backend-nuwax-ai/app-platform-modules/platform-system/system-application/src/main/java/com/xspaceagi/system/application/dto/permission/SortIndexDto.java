package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 顺序调整项
 */
@Data
public class SortIndexDto implements Serializable {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "父级ID，0表示根节点，不传则不修改（无层级则忽略）")
    private Long parentId;

    @Schema(description = "排序索引，不传则不修改")
    private Integer sortIndex;
}
