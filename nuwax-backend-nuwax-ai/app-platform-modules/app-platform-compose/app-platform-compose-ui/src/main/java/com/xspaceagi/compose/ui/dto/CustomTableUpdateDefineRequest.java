package com.xspaceagi.compose.ui.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.vo.data.FrontColumnDefineVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Schema(description = "更新表结构定义")
public class CustomTableUpdateDefineRequest {

    @Schema(description = "表ID")
    private Long id;

    @Schema(description = "表下面的字段定义列表")
    @JsonPropertyDescription("表下面的字段定义列表")
    private List<FrontColumnDefineVo> fieldList;

    /**
     * 转换为模型 ,前端给的字段
     */
    public static List<CustomFieldDefinitionModel> convert2Model(CustomTableUpdateDefineRequest request) {
        if (request == null) {
            return null;
        }
        // 字段转换
        List<CustomFieldDefinitionModel> fieldList = request.getFieldList().stream()
                .map(CustomTableDefinitionModel::convert2Model)
                .collect(Collectors.toList());
        return fieldList;
    }
}