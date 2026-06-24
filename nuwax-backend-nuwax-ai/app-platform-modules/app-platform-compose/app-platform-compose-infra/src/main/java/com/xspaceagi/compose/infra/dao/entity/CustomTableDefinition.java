package com.xspaceagi.compose.infra.dao.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义数据表定义
 */
@Schema(description = "自定义数据表定义")
@TableName(value = "custom_table_definition")
@Getter
@Setter
public class CustomTableDefinition {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 逻辑标记,1:有效;-1:无效
     */
    @Schema(description = "逻辑标记,1:有效;-1:无效")
    private Integer yn;

}