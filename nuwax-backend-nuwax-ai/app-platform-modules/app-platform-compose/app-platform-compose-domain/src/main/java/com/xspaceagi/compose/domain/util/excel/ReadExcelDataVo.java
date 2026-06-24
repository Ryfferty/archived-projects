package com.xspaceagi.compose.domain.util.excel;

import java.util.List;
import java.util.Map;

import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 读取excel数据返回结果
 */
@Getter
@Setter
public class ReadExcelDataVo {
    /**
     * 原始标题行
     */
    private List<String> originalHeader;
    /**
     * 处理后的标题行
     */
    private List<CustomFieldDefinitionModel> header;
    /**
     * 数据行
     */
    private List<Map<String, Object>> data;

    /**
     * 需要新增的字段,表头是纯中文的
     */
    private List<String> newFieldsToCreate;
}
