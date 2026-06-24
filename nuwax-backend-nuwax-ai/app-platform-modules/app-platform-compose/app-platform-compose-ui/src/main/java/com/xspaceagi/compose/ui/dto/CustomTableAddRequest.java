package com.xspaceagi.compose.ui.dto;

import com.xspaceagi.compose.domain.dto.CustomEmptyTableVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "新增表定义请求")
public class CustomTableAddRequest {
    
    @Schema(description = "表名称")
    private String tableName;
    
    @Schema(description = "表描述")
    private String tableDescription;
    
    @Schema(description = "空间ID")
    private Long spaceId;


    @Schema(description = "图标")
    private String icon;

    public static CustomEmptyTableVo convert2Model(CustomTableAddRequest request) {
        if (request == null) {
            return null;
        }
        CustomEmptyTableVo model = new CustomEmptyTableVo();
        model.setTableName(request.getTableName());
        model.setTableDescription(request.getTableDescription());
        model.setSpaceId(request.getSpaceId());
        model.setIcon(request.getIcon());
        return model;
    }
} 