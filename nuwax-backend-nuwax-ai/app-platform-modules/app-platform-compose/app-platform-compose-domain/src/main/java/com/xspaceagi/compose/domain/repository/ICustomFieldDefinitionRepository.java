package com.xspaceagi.compose.domain.repository;

import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface ICustomFieldDefinitionRepository {
  /**
   * 根据ID集合查询列表
   *
   * @param ids id集合
   * @return 列表
   */
  List<CustomFieldDefinitionModel> queryListByIds(List<Long> ids);

  /**
   * 根据主键查询 单条记录
   *
   * @param id id
   * @return 单条记录
   */
  CustomFieldDefinitionModel queryOneInfoById(Long id);

  /**
   * 批量新增
   * 
   * @param entityList  新增列表
   * @param userContext 用户上下文
   */
  void batchAddInfo(List<CustomFieldDefinitionModel> modelList, UserContext userContext);

  /**
   * 批量更新
   * 
   * @param entityList  更新列表
   * @param userContext 用户上下文
   */
  void batchUpdateInfo(List<CustomFieldDefinitionModel> modelList, UserContext userContext);

  /**
   * 删除根据主键id
   *
   * @param id id
   */
  void deleteById(Long id, UserContext userContext);

  /**
   * 根据表id删除
   *
   * @param tableId
   */
  void deleteByTableId(Long tableId);

  /**
   * 根据表id查询列表
   *
   * @param tableId
   * @return
   */
  List<CustomFieldDefinitionModel> queryListByTableId(Long tableId);

  /**
   * 根据表id集合查询列表
   *
   * @param tableIds
   * @return
   */
  List<CustomFieldDefinitionModel> queryListByTableIds(List<Long> tableIds);

}
