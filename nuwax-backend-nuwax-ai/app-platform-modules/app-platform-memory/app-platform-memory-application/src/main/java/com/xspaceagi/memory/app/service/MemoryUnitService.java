package com.xspaceagi.memory.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.memory.infra.dao.entity.MemoryUnit;
import com.xspaceagi.memory.sdk.dto.MemoryUnitCreateDTO;
import com.xspaceagi.memory.sdk.dto.MemoryUnitDTO;
import com.xspaceagi.memory.sdk.dto.MemoryUnitQueryDTO;
import com.xspaceagi.memory.sdk.dto.MemoryUnitUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * 记忆单元服务接口
 */
public interface MemoryUnitService extends IService<MemoryUnit> {

    /**
     * 创建记忆单元
     *
     * @param createDTO 创建DTO
     * @return 记忆单元DTO
     */
    MemoryUnitDTO create(MemoryUnitCreateDTO createDTO);

    /**
     * 更新记忆单元
     *
     * @param updateDTO 更新DTO
     * @return 记忆单元DTO
     */
    MemoryUnitDTO update(MemoryUnitUpdateDTO updateDTO);

    /**
     * 根据ID查询记忆单元
     *
     * @param id ID
     * @return 记忆单元DTO
     */
    MemoryUnitDTO getById(Long id);

    /**
     * 分页查询记忆单元
     *
     * @param queryDTO 查询条件
     * @return 记忆单元列表
     */
    List<MemoryUnitDTO> queryList(MemoryUnitQueryDTO queryDTO);

    /**
     * 根据用户ID和分类查询记忆单元
     *
     * @param userId   用户ID
     * @param category 分类
     * @return 记忆单元列表
     */
    List<MemoryUnitDTO> findByUserIdAndCategory(Long userId, Long agentId, String category, String subCategory);

    /**
     * 根据代理ID查询记忆单元
     *
     * @param agentId 代理ID
     * @return 记忆单元列表
     */
    List<MemoryUnitDTO> findByAgentId(Long agentId);

    /**
     * 根据分类和JSON字段值查询记忆单元
     *
     * @param tenantId    租户ID
     * @param userId      用户ID
     * @param category    一级分类
     * @param subCategory 二级分类（可选）
     * @param jsonKey     JSON中的键
     * @param jsonValue   JSON中的值
     * @return 记忆单元列表
     */
    List<MemoryUnitDTO> findByCategoryAndJsonKeyValue(Long tenantId, Long userId, String category, String subCategory, String jsonKey, String jsonValue);

    /**
     * 根据分类和JSON字段值查询记忆单元（支持模糊匹配）
     *
     * @param tenantId    租户ID
     * @param userId      用户ID
     * @param category    一级分类
     * @param subCategory 二级分类（可选）
     * @param jsonKey     JSON中的键
     * @param jsonValue   JSON中的值（支持模糊匹配）
     * @return 记忆单元列表
     */
    List<MemoryUnitDTO> findByCategoryAndJsonKeyValueLike(Long tenantId, Long userId, String category, String subCategory, String jsonKey, String jsonValue);

    /**
     * 根据JSON多个字段值查询记忆单元
     *
     * @param tenantId      租户ID
     * @param userId        用户ID
     * @param category      一级分类
     * @param subCategory   二级分类（可选）
     * @param jsonKeyValues JSON键值对 Map<key, value>
     * @return 记忆单元列表
     */
    List<MemoryUnitDTO> findByCategoryAndJsonKeyValues(Long tenantId, Long userId, String category, String subCategory, Map<String, String> jsonKeyValues);

    /**
     * 实体转DTO
     *
     * @param entity 实体
     * @return DTO
     */
    MemoryUnitDTO toDTO(MemoryUnit entity);
}
