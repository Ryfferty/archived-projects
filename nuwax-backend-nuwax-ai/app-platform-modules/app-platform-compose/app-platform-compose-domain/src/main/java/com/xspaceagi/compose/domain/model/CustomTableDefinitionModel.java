package com.xspaceagi.compose.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.util.CollectionUtils;

import com.xspaceagi.compose.sdk.vo.data.FrontColumnDefineVo;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;
import com.xspaceagi.compose.sdk.vo.define.TableFieldDefineVo;
import com.xspaceagi.system.spec.enums.YnEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义数据表定义
 */
@Schema(description = "自定义数据表定义")
@Getter
@Setter
public class CustomTableDefinitionModel {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 所属空间ID
     */
    @Schema(description = "所属空间ID")
    private Long spaceId;

    /**
     * 图标图片地址
     */
    @Schema(description = "图标图片地址")
    private String icon;

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;

    /**
     * 表描述
     */
    @Schema(description = "表描述")
    private String tableDescription;

    /**
     * Doris数据库名
     */
    @Schema(description = "Doris数据库名")
    private String dorisDatabase;

    /**
     * Doris表名
     */
    @Schema(description = "Doris表名")
    private String dorisTable;

    /**
     * 状态：1-启用 -1-禁用
     */
    @Schema(description = "状态：1-启用 -1-禁用")
    private Integer status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime created;

    /**
     * 创建人id
     */
    @Schema(description = "创建人id")
    private Long creatorId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String creatorName;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime modified;

    /**
     * 最后修改人id
     */
    @Schema(description = "最后修改人id")
    private Long modifiedId;

    /**
     * 最后修改人
     */
    @Schema(description = "最后修改人")
    private String modifiedName;


    @Schema(description = "原始建表DDL")
    @JsonPropertyDescription("原始建表DDL")
    private String createTableDdl;

    /**
     * 表字段列表
     */
    @Schema(description = "表字段列表")
    private List<CustomFieldDefinitionModel> fieldList;

    /**
     * 转换为表定义VO
     * 
     * @param tableInfo
     * @return
     */
    public static TableDefineVo convertToTableDefineVo(CustomTableDefinitionModel tableInfo) {
        TableDefineVo tableDefineVo = TableDefineVo.builder()
                .id(tableInfo.getId())
                .tenantId(tableInfo.getTenantId())
                .spaceId(tableInfo.getSpaceId())
                .icon(tableInfo.getIcon())
                .tableName(tableInfo.getTableName())
                .tableDescription(tableInfo.getTableDescription())
                .dorisDatabase(tableInfo.getDorisDatabase())
                .dorisTable(tableInfo.getDorisTable())
                .created(tableInfo.getCreated())
                .creatorId(tableInfo.getCreatorId())
                .creatorName(tableInfo.getCreatorName())
                .modified(tableInfo.getModified())
                .build();

        if (!CollectionUtils.isEmpty(tableInfo.getFieldList())) {
            List<TableFieldDefineVo> fieldDefineVos = tableInfo.getFieldList().stream()
                    .map(CustomFieldDefinitionModel::convertToTableFieldDefineVo)
                    .collect(Collectors.toList());
            tableDefineVo.setFieldList(fieldDefineVos);
        }

        return tableDefineVo;
    }

    /**
     * 转换为自定义字段定义模型
     * 
     * @param request
     * @return
     */
    public static CustomFieldDefinitionModel convert2Model(FrontColumnDefineVo request) {
        if (request == null) {
            return null;
        }
        CustomFieldDefinitionModel model = CustomFieldDefinitionModel.builder()
                .id(request.getId())
                .fieldName(request.getFieldName())
                .fieldDescription(request.getFieldDescription())
                .fieldType(request.getFieldType())
                .nullableFlag(request.getNullableFlag() ? YnEnum.Y.getKey() : YnEnum.N.getKey())
                .defaultValue(request.getDefaultValue())
                .uniqueFlag(request.getUniqueFlag() ? YnEnum.Y.getKey() : YnEnum.N.getKey())
                .enabledFlag(request.getEnabledFlag() ? YnEnum.Y.getKey() : YnEnum.N.getKey())
                .sortIndex(request.getSortIndex())
                .systemFieldFlag(request.getSystemFieldFlag() ? YnEnum.Y.getKey() : YnEnum.N.getKey())
                .build();
        return model;
    }

}