package com.xspaceagi.memory.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.memory.infra.dao.entity.MemoryUnitTag;
import com.xspaceagi.memory.sdk.dto.MemoryUnitTagCreateDTO;
import com.xspaceagi.memory.sdk.dto.MemoryUnitTagDTO;

import java.util.List;

/**
 * 记忆单元标签服务接口
 */
public interface MemoryUnitTagService extends IService<MemoryUnitTag> {

    /**
     * 创建标签
     *
     * @param createDTO 创建DTO
     * @return 标签DTO
     */
    MemoryUnitTagDTO create(MemoryUnitTagCreateDTO createDTO);

    /**
     * 实体转DTO
     *
     * @param entity 实体
     * @return DTO
     */
    MemoryUnitTagDTO toDTO(MemoryUnitTag entity);


    List<MemoryUnitTagDTO> searchByTagNames(Long userId, List<String> tags);
}
