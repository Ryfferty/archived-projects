package com.xspaceagi.compose.sdk.vo.doris;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Doris表字段定义
 */
@Data
@Schema(description = "Doris表字段定义")
@Builder
public class DorisTableFieldVo {

    @Schema(description = "字段名")
    @JsonPropertyDescription("字段名")
    private String fieldName;

    /**
     * 字段类型：1:String;2:Integer;3:Number;4:Boolean;5:Date
     * @see com.xspaceagi.compose.sdk.enums.TableFieldTypeEnum
     */
    @Schema(description = "字段类型：1:String;2:Integer;3:Number;4:Boolean;5:Date")
    @JsonPropertyDescription("字段类型：1:String;2:Integer;3:Number;4:Boolean;5:Date")
    private Integer fieldType;

    @Schema(description = "字段类型描述")
    @JsonPropertyDescription("字段类型描述")
    private String fieldTypeDesc;

    @Schema(description = "是否可为空")
    @JsonPropertyDescription("是否可为空")
    private Boolean nullable;

    @Schema(description = "默认值")
    @JsonPropertyDescription("默认值")
    private String defaultValue;

    @Schema(description = "字段注释")
    @JsonPropertyDescription("字段注释")
    private String comment;

    /**
     * 字段顺序
     */
    @Schema(description = "字段顺序")
    @JsonPropertyDescription("字段顺序")
    private Integer sortIndex;


}