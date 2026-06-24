package com.xspaceagi.compose.application.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.xspaceagi.compose.application.model.QueryDorisTableDefinePageRequestVo;
import com.xspaceagi.compose.domain.dto.CustomAddBusinessRowDataVo;
import com.xspaceagi.compose.domain.dto.CustomDeleteBusinessRowDataVo;
import com.xspaceagi.compose.domain.dto.CustomEmptyTableVo;
import com.xspaceagi.compose.domain.dto.CustomUpdateBusinessRowDataVo;
import com.xspaceagi.compose.domain.model.CustomDorisDataRequest;
import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.DorisDataPage;
import com.xspaceagi.compose.sdk.vo.define.CreateTableDefineVo;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.page.PageQueryVo;
import com.xspaceagi.system.spec.page.SuperPage;

/**
 * 自定义 数据表服务
 */
public interface CustomTableDefinitionApplicationService {

    /**
     * 新增空表定义
     *
     * @param model
     * @param userContext
     * @return
     */
    Long addInfo(CustomEmptyTableVo model, UserContext userContext);

    /**
     * 新增空表定义,不检查空间权限
     *
     * @param model
     * @param userContext
     * @return
     */
    Long myAddInfoNoCheckSpace(CustomEmptyTableVo model, UserContext userContext);

    /**
     * 更新表定义
     *
     * @param model
     * @param userContext
     */
    void updateInfo(Long tableId, List<CustomFieldDefinitionModel> fieldList, UserContext userContext);

    /**
     * 更新表定义,不检查空间权限
     *
     * @param tableId
     * @param fieldList
     * @param userContext
     */
    void myUpdateInfoNoCheckSpace(Long tableId, List<CustomFieldDefinitionModel> fieldList, UserContext userContext);

    /**
     * 更新表名称
     *
     * @param model
     * @param userContext
     */
    void updateTableName(CustomTableDefinitionModel model, UserContext userContext);

    /**
     * 查询表定义
     *
     * @param tableId 表定义ID
     * @return
     */
    CustomTableDefinitionModel queryOneTableDefineVo(Long tableId);

    /**
     * 删除表定义
     *
     * @param tableId
     * @param userContext
     */
    void deleteById(Long tableId, UserContext userContext);

    /**
     * 查询doris表数据,分页查询;给前端使用的,分页查询
     *
     * @param request
     * @return
     */
    DorisDataPage<Map<String, Object>> queryPageDorisTableDataForWeb(CustomDorisDataRequest request);

    /**
     * 根据表ID和行ID查询单行数据
     *
     * @param tableId 表ID
     * @param rowId   行数据ID
     * @return 行数据，如果不存在返回 null
     */
    Map<String, Object> queryRowDataByTableIdAndRowId(Long tableId, Long rowId);

    /**
     * 导出Doris表数据到Excel
     *
     * @param tableId      表定义ID
     * @param outputStream 输出流，用于写入Excel数据
     * @param userContext  用户上下文
     */
    void exportTableDataToExcel(Long tableId, OutputStream outputStream, UserContext userContext);

    /**
     * 新增业务数据
     *
     * @param request
     * @param userContext
     */
    void addBusinessData(CustomAddBusinessRowDataVo request, UserContext userContext);

    /**
     * 修改业务数据,行数修改
     *
     * @param request
     * @param userContext
     */
    void updateBusinessData(CustomUpdateBusinessRowDataVo request, UserContext userContext);

    /**
     * 删除业务数据,行数删除
     *
     * @param request     删除请求对象（包含 tableId 和 rowId）
     * @param rowData     要删除的完整行数据（用于日志记录以便恢复）
     * @param userContext 用户上下文
     */
    void deleteBusinessData(CustomDeleteBusinessRowDataVo request, Map<String, Object> rowData, UserContext userContext);

    /**
     * 清空业务数据
     *
     * @param tableId
     * @param userContext
     */
    void clearBusinessData(Long tableId, UserContext userContext);

    /**
     * 分页查询表定义
     *
     * @param request
     * @return
     */
    SuperPage<CustomTableDefinitionModel> queryPageTableDefine(
            PageQueryVo<QueryDorisTableDefinePageRequestVo> pageQueryVo);

    /**
     * 根据空间ID查询表定义
     *
     * @param spaceId
     * @return
     */
    List<CustomTableDefinitionModel> queryTableDefineBySpaceId(Long spaceId);

    /**
     * 查询是否存在业务数据
     *
     * @param tableId
     * @return
     */
    Boolean existTableData(Long tableId);

    /**
     * 复制表结构定义
     *
     * @param tableId
     * @param userContext
     * @return
     */
    Long copyTableDefinition(Long tableId, UserContext userContext);

    /**
     * 导入业务表数据Excel
     *
     * @param tableId
     * @param file
     * @param userContext
     */
    void importTableDataFromExcel(Long tableId, InputStream inputStream, UserContext userContext);


    /**
     * 创建表结构定义
     *
     * @param tableDefineVo 创建表结构定义
     * @return 数据表的创建成功的主键ID
     */
    Long createTable(CreateTableDefineVo tableDefineVo);

    /**
     * 根据操作日志的 extraContent 恢复被删除的业务数据
     *
     * @param extraContent 操作日志的 extraContent JSON 字符串
     * @param forceRestore 是否强制恢复（覆盖已存在的数据）
     * @param userContext  用户上下文
     * @return 恢复结果信息
     */
    String restoreBusinessDataByExtraContent(String extraContent, Boolean forceRestore, UserContext userContext);

    /**
     * 统计用户下所有表的数量
     *
     * @param userId 用户ID
     * @return 用户下所有表的数量
     */
    Long countUserTotalTable(Long userId);
}
