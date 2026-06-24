package com.xspaceagi.compose.ui.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.vo.data.FrontColumnDefineVo;
import com.xspaceagi.system.spec.enums.YnEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "表结构定义")
@Getter
@Setter
@Builder
public class FrontTableDefineResponseVo {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @JsonPropertyDescription("主键ID")
    private Long id;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @JsonPropertyDescription("租户ID")
    private Long tenantId;

    /**
     * 所属空间ID
     */
    @Schema(description = "所属空间ID")
    @JsonPropertyDescription("所属空间ID")
    private Long spaceId;

    /**
     * 图标图片地址
     */
    @Schema(description = "图标图片地址")
    @JsonPropertyDescription("图标图片地址")
    private String icon;

    /**
     * 表名
     */
    @Schema(description = "表名")
    @JsonPropertyDescription("表名")
    private String tableName;

    /**
     * 表描述
     */
    @Schema(description = "表描述")
    @JsonPropertyDescription("表描述")
    private String tableDescription;

    /**
     * Doris数据库名
     */
    @Schema(description = "Doris数据库名")
    @JsonPropertyDescription("Doris数据库名")
    private String dorisDatabase;

    /**
     * Doris表名
     */
    @Schema(description = "Doris表名")
    @JsonPropertyDescription("Doris表名")
    private String dorisTable;

    @Schema(description = "表下面的字段定义列表")
    @JsonPropertyDescription("表下面的字段定义列表")
    private List<FrontColumnDefineVo> fieldList;

    @Schema(description = "原始建表DDL")
    @JsonPropertyDescription("原始建表DDL")
    private String createTableDdl;

    @Schema(description = "是否存在业务数据,true:存在数据;false:不存在数据")
    @JsonPropertyDescription("是否存在业务数据,true:存在数据;false:不存在数据")
    private Boolean existTableDataFlag;

    /**
     * 转换为前端表定义响应VO
     * 
     * @param tableModel 表定义模型
     * @return 前端表定义响应VO
     */
    public static FrontTableDefineResponseVo convert2FrontTableDefineResponseVo(CustomTableDefinitionModel tableModel) {

        FrontTableDefineResponseVo responseVo = FrontTableDefineResponseVo.builder()
                .id(tableModel.getId())
                .tenantId(tableModel.getTenantId())
                .spaceId(tableModel.getSpaceId())
                .icon(tableModel.getIcon())
                .tableName(tableModel.getTableName())
                .tableDescription(tableModel.getTableDescription())
                .dorisDatabase(tableModel.getDorisDatabase())
                .dorisTable(tableModel.getDorisTable())
                .createTableDdl(tableModel.getCreateTableDdl())
                .existTableDataFlag(true)
                .build();

        // 字段列表
        List<FrontColumnDefineVo> fieldList = new ArrayList<>();
        for (CustomFieldDefinitionModel field : tableModel.getFieldList()) {
            FrontColumnDefineVo columnDefineVo = FrontColumnDefineVo.builder()
                    .id(field.getId())
                    .systemFieldFlag(field.getSystemFieldFlag() == YnEnum.Y.getKey())
                    .fieldName(field.getFieldName())
                    .fieldDescription(field.getFieldDescription())
                    .fieldType(field.getFieldType())
                    .nullableFlag(field.getNullableFlag() == YnEnum.Y.getKey())
                    .defaultValue(field.getDefaultValue())
                    .uniqueFlag(field.getUniqueFlag() == YnEnum.Y.getKey())
                    .enabledFlag(field.getEnabledFlag() == YnEnum.Y.getKey())
                    .sortIndex(field.getSortIndex())
                    .build();
            fieldList.add(columnDefineVo);
        }

        responseVo.setFieldList(fieldList);
        responseVo.setCreateTableDdl(tableModel.getCreateTableDdl());
        return responseVo;

    }

}
