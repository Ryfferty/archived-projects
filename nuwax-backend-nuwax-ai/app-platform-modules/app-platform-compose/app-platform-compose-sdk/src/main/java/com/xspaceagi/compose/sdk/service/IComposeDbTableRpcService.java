package com.xspaceagi.compose.sdk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.compose.sdk.DorisDataPage;
import com.xspaceagi.compose.sdk.request.DorisTableDataRequest;
import com.xspaceagi.compose.sdk.request.DorisTableDefineRequest;
import com.xspaceagi.compose.sdk.request.DorisToolTableDefineRequest;
import com.xspaceagi.compose.sdk.request.QueryDorisTableDefinePageRequest;
import com.xspaceagi.compose.sdk.response.DorisTableDataResponse;
import com.xspaceagi.compose.sdk.response.DorisToolTableDefineResponse;
import com.xspaceagi.compose.sdk.vo.define.CreateTableDefineVo;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;

import java.util.List;

/**
 * 提供数据表功能的查询服务,包含表结构信息查询,表数据查询等;另外支持 直接写sql查询, 支持自定义sql查询;
 */
public interface IComposeDbTableRpcService {

    /**
     * 查询表结构信息
     * 
     * @param request 请求参数
     * @return 表结构定义信息
     */
    TableDefineVo queryTableDefinition(DorisTableDefineRequest request);

    /**
     * 查询表数据
     * 
     * @param request 请求参数
     * @return 表数据
     */
    DorisTableDataResponse queryTableData(DorisTableDataRequest request);

    /**
     * 查询用户可以使用的数据表组件的,对应的表结构定义信息;不分页,查询全部
     * 
     * @param request 请求参数
     * @return 表结构定义信息列表
     */
    DorisToolTableDefineResponse queryUserToolTableDefine(DorisToolTableDefineRequest request);

    /**
     * 根据空间id,分页查询数据表定义信息
     * 
     * @param request 请求参数
     * @return 表定义信息列表
     */
    DorisDataPage<TableDefineVo> queryTableDefineBySpaceId(QueryDorisTableDefinePageRequest request);

    /**
     * 根据空间id,查询数据表定义信息
     *
     * @param spaceId 空间id
     * @return 表定义信息列表
     */
    List<TableDefineVo> queryListBySpaceId(Long spaceId);

    /**
     * 创建表
     * 
     * @param tableDefineVo 表定义信息
     */
    Long createTable(CreateTableDefineVo tableDefineVo);

    /**
     * 查询创建表的表结构定义信息
     *
     * @param request 请求参数
     * @return 表结构定义信息
     */
    CreateTableDefineVo queryCreateTableInfo(DorisTableDefineRequest request);

    /**
     * 统计数据表总数
     *
     * @return 数据表总数
     */
    Long countTotalTables();

    /**
     * 管理端查询数据表列表
     *
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @param name 名称模糊搜索
     * @param creatorIds 创建人ID列表
     * @param spaceId 空间ID
     * @return 数据表分页数据
     */
    IPage<TableDefineVo> queryListForManage(Integer pageNo, Integer pageSize, String name, java.util.List<Long> creatorIds, Long spaceId);

    /**
     * 管理端删除数据表
     *
     * @param id 表ID
     */
    void deleteForManage(Long id);

    /**
     * 查询用户数据表总数
     *
     * @param userId 用户ID
     * @return 数据表总数
     */
    Long countUserTotalTable(Long userId);
}
