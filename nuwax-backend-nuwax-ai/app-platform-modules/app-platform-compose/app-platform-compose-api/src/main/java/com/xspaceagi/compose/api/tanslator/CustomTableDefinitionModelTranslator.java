package com.xspaceagi.compose.api.tanslator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;
import com.xspaceagi.compose.sdk.vo.define.TableFieldDefineVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义表定义模型翻译器
 */
@Slf4j
public class CustomTableDefinitionModelTranslator {

    /**
     * 翻译表定义模型
     * 
     * @param model 表定义模型
     * @return 表定义模型
     */
    public static TableDefineVo translate(CustomTableDefinitionModel model) {
        TableDefineVo tableDefineVo = TableDefineVo.builder()
                .id(model.getId())
                .tenantId(model.getTenantId())
                .spaceId(model.getSpaceId())
                .icon(model.getIcon())
                .tableName(model.getTableName())
                .tableDescription(model.getTableDescription())
                .dorisDatabase(model.getDorisDatabase())
                .dorisTable(model.getDorisTable())
                .created(model.getCreated())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .modifiedId(model.getModifiedId())
                .modifiedName(model.getModifiedName())
                .build();
        // 判断 model.getFieldList() 是否为空,空则默认 List.of()
        if (CollectionUtils.isEmpty(model.getFieldList())) {
            tableDefineVo.setFieldList(List.of());
        } else {
            var fieldList = model.getFieldList().stream()
                    .map(CustomTableDefinitionModelTranslator::translateFieldList)
                    .collect(Collectors.toList());
            tableDefineVo.setFieldList(fieldList);
        }

        return tableDefineVo;

    }

    /**
     * 翻译字段定义模型
     * 
     * @param fieldDefine 字段定义模型
     * @return 表字段定义模型
     */
    public static TableFieldDefineVo translateFieldList(CustomFieldDefinitionModel fieldDefine) {
        TableFieldDefineVo tableFieldDefineVo = TableFieldDefineVo.builder()
                .fieldName(fieldDefine.getFieldName())
                .systemFieldFlag(fieldDefine.getSystemFieldFlag())
                .fieldDescription(fieldDefine.getFieldDescription())
                .fieldType(fieldDefine.getFieldType())
                .nullableFlag(fieldDefine.getNullableFlag())
                .defaultValue(fieldDefine.getDefaultValue())
                .uniqueFlag(fieldDefine.getUniqueFlag())
                .enabledFlag(fieldDefine.getEnabledFlag())
                .sortIndex(fieldDefine.getSortIndex())
                .build();
        return tableFieldDefineVo;

    }
}
