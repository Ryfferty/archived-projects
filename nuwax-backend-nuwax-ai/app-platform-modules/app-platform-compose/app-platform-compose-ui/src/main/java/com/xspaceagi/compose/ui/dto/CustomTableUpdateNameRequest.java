package com.xspaceagi.compose.ui.dto;

import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新表名称和描述请求")
public class CustomTableUpdateNameRequest {

    @Schema(description = "表ID")
    private Long id;

    @Schema(description = "表名称")
    private String tableName;

    @Schema(description = "表描述")
    private String tableDescription;

    @Schema(description = "表图标")
    private String icon;

    public static CustomTableDefinitionModel convert2Model(CustomTableUpdateNameRequest request) {
        if (request == null) {
            return null;
        }
        CustomTableDefinitionModel model = new CustomTableDefinitionModel();
        model.setId(request.getId());
        model.setTableName(request.getTableName());
        model.setTableDescription(request.getTableDescription());
        model.setIcon(request.getIcon());
        return model;
    }
}