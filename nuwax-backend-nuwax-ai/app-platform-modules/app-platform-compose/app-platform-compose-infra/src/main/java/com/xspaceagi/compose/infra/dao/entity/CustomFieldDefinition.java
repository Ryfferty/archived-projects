package com.xspaceagi.compose.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 自定义字段定义
 */
@Schema(description = "自定义字段定义")
@TableName(value = "custom_field_definition")
@Getter
@Setter
@Builder
public class CustomFieldDefinition {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(value = "_tenant_id")
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
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
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
     * 逻辑标记,1:有效;-1:无效
     */
    @Schema(description = "逻辑标记,1:有效;-1:无效")
    private Integer yn;


}