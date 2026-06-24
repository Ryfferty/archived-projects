package com.xspaceagi.compose.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.xspaceagi.compose.sdk.enums.DefaultTableFieldEnum;
import com.xspaceagi.compose.sdk.vo.data.FrontColumnDefineVo;
import com.xspaceagi.compose.sdk.vo.define.TableFieldDefineVo;
import com.xspaceagi.compose.sdk.vo.doris.DorisTableFieldVo;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.enums.YnEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义字段定义
 */
@Schema(description = "自定义字段定义")
@Getter
@Setter
@Builder
public class CustomFieldDefinitionModel {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 是否为系统字段,1:系统字段;-1:非系统字段
     */
    @Schema(description = "是否为系统字段,1:系统字段;-1:非系统字段")
    private Integer systemFieldFlag;

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
     * 关联的表ID
     */
    @Schema(description = "关联的表ID")
    private Long tableId;

    /**
     * 字段名
     */
    @Schema(description = "字段名")
    private String fieldName;

    /**
     * 字段描述
     */
    @Schema(description = "字段描述")
    private String fieldDescription;

    /**
     * 字段类型：1:String;2:Integer;3:Number;4:Boolean;5:Date
     */
    @Schema(description = "字段类型：1:String;2:Integer;3:Number;4:Boolean;5:Date")
    private Integer fieldType;

    /**
     * 是否可为空：1-可空 -1-非空
     */
    @Schema(description = "是否可为空：1-可空 -1-非空")
    private Integer nullableFlag;

    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private String defaultValue;

    /**
     * 是否唯一：1-唯一 -1-非唯一
     */
    @Schema(description = "是否唯一：1-唯一 -1-非唯一")
    private Integer uniqueFlag;

    /**
     * 是否启用：1-启用 -1-禁用
     */
    @Schema(description = "是否启用：1-启用 -1-禁用")
    private Integer enabledFlag;

    /**
     * 字段顺序
     */
    @Schema(description = "字段顺序")
    private Integer sortIndex;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime created;

    /**
     * 字符串字段长度,可空,比如字符串,可以指定长度使用
     */
    @Schema(description = "字符串字段长度,可空,比如字符串,可以指定长度使用")
    private Integer fieldStrLen;

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

    /**
     * 转换为表字段定义VO
     * 
     * @param field
     * @return
     */
    public static TableFieldDefineVo convertToTableFieldDefineVo(CustomFieldDefinitionModel field) {
        TableFieldDefineVo fieldVo = TableFieldDefineVo.builder()
                .id(field.getId())
                .systemFieldFlag(field.getSystemFieldFlag())
                .fieldName(field.getFieldName())
                .fieldDescription(field.getFieldDescription())
                .fieldType(field.getFieldType())
                .nullableFlag(field.getNullableFlag())
                .uniqueFlag(field.getUniqueFlag())
                .enabledFlag(field.getEnabledFlag())
                .defaultValue(field.getDefaultValue())
                .build();
        return fieldVo;
    }

    /**
     * 获取表的系统字段,第一次新增空白表结构的时候使用
     * 
     * @param tableId     表ID
     * @param spaceId     空间ID
     * @param userContext 用户上下文
     * @return 系统字段列表
     */
    public static List<CustomFieldDefinitionModel> obatinTableSystemFields(Long tableId, Long spaceId,
            UserContext userContext) {
        var systemFields = DefaultTableFieldEnum.getAllDefaultFields();

        List<CustomFieldDefinitionModel> fieldList = new ArrayList<>();
        AtomicInteger sortIndexCounter = new AtomicInteger(0);
        for (TableFieldDefineVo field : systemFields) {
            CustomFieldDefinitionModel fieldModel = CustomFieldDefinitionModel.builder()
                    .id(null)
                    .systemFieldFlag(YnEnum.Y.getKey())
                    .tableId(tableId)
                    .fieldName(field.getFieldName())
                    .fieldDescription(field.getFieldDescription())
                    .fieldType(field.getFieldType())
                    .nullableFlag(field.getNullableFlag())
                    .uniqueFlag(field.getUniqueFlag())
                    .enabledFlag(field.getEnabledFlag())
                    .defaultValue(field.getDefaultValue())
                    .sortIndex(sortIndexCounter.getAndIncrement())
                    .spaceId(spaceId)
                    .tenantId(userContext.getTenantId())
                    .creatorId(userContext.getUserId())
                    .creatorName(userContext.getUserName())
                    .build();
            fieldList.add(fieldModel);
        }

        return fieldList;

    }

    /**
     * 转换为给前端用的表字段定义
     * 
     * @param field 自定义字段定义
     * @return Excel抬头字段定义VO
     */
    public static FrontColumnDefineVo convertToExcelColumnDefineVo(CustomFieldDefinitionModel field) {
        FrontColumnDefineVo columnDefineVo = FrontColumnDefineVo.builder()
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
        return columnDefineVo;
    }

    /**
     * 转换为Doris表字段定义
     * 
     * @param field 自定义字段定义
     * @return Doris表字段定义
     */
    public static DorisTableFieldVo convertToDorisTableFieldVo(CustomFieldDefinitionModel field) {
        DorisTableFieldVo fieldVo = DorisTableFieldVo.builder()
                .fieldName(field.getFieldName())
                .fieldType(field.getFieldType())
                .nullable(field.getNullableFlag() == YnEnum.Y.getKey())
                .defaultValue(field.getDefaultValue())
                .comment(field.getFieldDescription())
                .sortIndex(field.getSortIndex())
                .build();
        return fieldVo;
    }



    public static CustomFieldDefinitionModel convertToModelForCreate(TableFieldDefineVo field) {
        CustomFieldDefinitionModel fieldModel = CustomFieldDefinitionModel.builder()
                .id(null)
                .systemFieldFlag(field.getSystemFieldFlag())
                .fieldName(field.getFieldName())
                .fieldDescription(field.getFieldDescription())
                .fieldType(field.getFieldType())
                .nullableFlag(field.getNullableFlag())
                .uniqueFlag(field.getUniqueFlag())
                .enabledFlag(field.getEnabledFlag())
                .defaultValue(field.getDefaultValue())
                .sortIndex(field.getSortIndex())
                .spaceId(null)
                .tenantId(null)
                .creatorId(null)
                .creatorName(null)
                .modifiedId(null)
                .modifiedName(null)
                .build();
        return fieldModel;
    }

    public static CustomFieldDefinitionModel createFromExcelHeader(
            CustomTableDefinitionModel tableModel,
            String headerName,
            String newFieldName,
            int sortIndex,
            UserContext userContext) {
        return CustomFieldDefinitionModel.builder()
                .fieldName(newFieldName)
                .fieldDescription(headerName)
                .fieldType(com.xspaceagi.compose.sdk.enums.TableFieldTypeEnum.MEDIUMTEXT.getCode())
                .nullableFlag(1)
                .enabledFlag(1)
                .tableId(tableModel.getId())
                .tenantId(tableModel.getTenantId())
                .spaceId(tableModel.getSpaceId())
                .uniqueFlag(-1)
                .systemFieldFlag(-1)
                .sortIndex(sortIndex)
                .creatorId(userContext.getUserId())
                .creatorName(userContext.getUserName())
                .build();
    }

}