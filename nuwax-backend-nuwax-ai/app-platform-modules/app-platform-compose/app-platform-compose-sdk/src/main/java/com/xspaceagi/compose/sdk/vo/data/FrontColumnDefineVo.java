package com.xspaceagi.compose.sdk.vo.data;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 给前端页面使用,和内部使用的字段模型区别,内部字段标识都是 :1和-1; 这里转换为了Boolean属性类型
 */
@Data
@Schema(description = "数据表业务表结构的字段定义")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontColumnDefineVo implements Serializable {

  /**
   * 主键ID
   */
  @Schema(description = "主键ID")
  private Long id;

  /**
   * 是否为系统字段
   */
  @Schema(description = "是否为系统字段")
  private Boolean systemFieldFlag;

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
   * 字段类型：1:String;2:Integer;3:Number;4:Boolean;5:Date;6:PrimaryKey;7:MEDIUMTEXT
   * @see com.xspaceagi.compose.sdk.enums.TableFieldTypeEnum
   */
  @Schema(description = "字段类型：1:String(VARCHAR(255));2:Integer(INT);3:Number(DECIMAL(20,6));4:Boolean(TINYINT(1));5:Date(DATETIME);6:PrimaryKey(BIGINT);7:MEDIUMTEXT(MEDIUMTEXT)")
  private Integer fieldType;

  /**
   * 是否可为空
   */
  @Schema(description = "是否可为空,true:可空;false:非空")
  private Boolean nullableFlag;

  /**
   * 默认值
   */
  @Schema(description = "默认值")
  private String defaultValue;

  /**
   * 是否唯一
   */
  @Schema(description = "是否唯一,true:唯一;false:非唯一")
  private Boolean uniqueFlag;

  /**
   * 是否启用
   */
  @Schema(description = "是否启用：true:启用;false:禁用")
  private Boolean enabledFlag;

  /**
   * 字段顺序
   */
  @Schema(description = "字段顺序")
  private Integer sortIndex;

}
