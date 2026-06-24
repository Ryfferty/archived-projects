package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TableCustomSqlNodeConfigDto extends TableNodeConfigDto {

    @Schema(description = "SQL语句")
    private String sql;
}
