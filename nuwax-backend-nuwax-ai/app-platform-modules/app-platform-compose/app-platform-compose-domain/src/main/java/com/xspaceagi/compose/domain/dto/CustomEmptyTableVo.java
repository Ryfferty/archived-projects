package com.xspaceagi.compose.domain.dto;

import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.vo.define.CreateTableDefineVo;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.enums.YnEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 新增 空表定义
 */
@Getter
@Setter
public class CustomEmptyTableVo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableDescription;
    


    /**
     * 所属空间ID
     */
    private Long spaceId;


    @Schema(description = "图标")
    private String icon;

    /**
     * 转换为表定义模型
     * @param model
     * @return
     */
    public static CustomTableDefinitionModel convertToModel(CustomEmptyTableVo model,UserContext userContext) {
        // 1. 创建表定义模型
        CustomTableDefinitionModel tableModel = new CustomTableDefinitionModel();
        tableModel.setTableName(model.getTableName());
        tableModel.setTableDescription(model.getTableDescription());
        tableModel.setSpaceId(model.getSpaceId());
        tableModel.setIcon(model.getIcon());
        // 默认启用
        tableModel.setStatus(YnEnum.Y.getKey()); 

        //新增时，doris表名未确定，先设置为空,等入库后,用id拼接生成doris表名
        tableModel.setDorisTable("");
        tableModel.setCreatorId(userContext.getUserId());
        tableModel.setCreatorName(userContext.getUserName());

        return tableModel;
    }



    /**
     * 通过api接口,来创建数据表使用, 将表定义模型转换为空表定义模型
     * @param model 表定义模型
     * @return
     */
    public static CustomEmptyTableVo convertToVo(CreateTableDefineVo model) {
        CustomEmptyTableVo vo = new CustomEmptyTableVo();
        vo.setTableName(model.getTableName());
        vo.setTableDescription(model.getTableDescription());
        vo.setSpaceId(model.getSpaceId());
        vo.setIcon(model.getIcon());
        return vo;
    }
    
}
