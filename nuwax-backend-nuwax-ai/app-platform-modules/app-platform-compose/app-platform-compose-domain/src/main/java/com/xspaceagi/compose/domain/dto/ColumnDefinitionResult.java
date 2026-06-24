package com.xspaceagi.compose.domain.dto;

import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.vo.data.FrontColumnDefineVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 获取列定义信息 (内部类用于返回多个值)
 */
@Getter
@Setter
@AllArgsConstructor
public class ColumnDefinitionResult {

    private CustomTableDefinitionModel tableModel;
    private List<FrontColumnDefineVo> columnDefines;
    private List<String> orderedFieldNames;
    private List<CustomFieldDefinitionModel> fields;
}
