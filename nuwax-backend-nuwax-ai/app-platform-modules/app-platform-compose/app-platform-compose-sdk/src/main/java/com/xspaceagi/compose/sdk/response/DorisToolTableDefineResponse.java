package com.xspaceagi.compose.sdk.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * doris 用户可以使用的数据表组件的,对应的表结构定义信息查询响应
 */
@Schema(description = "doris 用户可以使用的数据表组件的,对应的表结构定义信息查询响应")
@Getter
@Setter
public class DorisToolTableDefineResponse implements Serializable {

    /**
     * 表结构定义
     */
    @Schema(description = "表结构定义列表")
    @JsonPropertyDescription("表结构定义列表")
    private List<TableDefineVo> tableDefineList;

}
