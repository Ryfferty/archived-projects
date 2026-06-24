package com.xspaceagi.memory.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.memory.app.service.MemoryUnitTagService;
import com.xspaceagi.memory.infra.dao.entity.MemoryUnitTag;
import com.xspaceagi.memory.infra.dao.mapper.MemoryUnitTagMapper;
import com.xspaceagi.memory.sdk.dto.MemoryUnitTagCreateDTO;
import com.xspaceagi.memory.sdk.dto.MemoryUnitTagDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 记忆单元标签服务实现
 */
@Slf4j
@Service
public class MemoryUnitTagServiceImpl extends ServiceImpl<MemoryUnitTagMapper, MemoryUnitTag> implements MemoryUnitTagService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemoryUnitTagDTO create(MemoryUnitTagCreateDTO createDTO) {
        log.info("创建记忆标签: {}", createDTO);

        MemoryUnitTag entity = MemoryUnitTag.builder()
                .tenantId(createDTO.getUserId()) // 这里可以根据实际业务逻辑获取租户ID
                .userId(createDTO.getUserId())
                .memoryId(createDTO.getMemoryId())
                .tagName(createDTO.getTagName())
                .created(new Date())
                .build();

        save(entity);
        log.info("记忆标签创建成功: {}", entity.getId());
        return toDTO(entity);
    }

    @Override
    public List<MemoryUnitTagDTO> searchByTagNames(Long userId, List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }
        List<MemoryUnitTag> list = list(new QueryWrapper<MemoryUnitTag>().eq("user_id", userId).in("tag_name", tags));
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public MemoryUnitTagDTO toDTO(MemoryUnitTag entity) {
        if (entity == null) {
            return null;
        }

        return MemoryUnitTagDTO.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .userId(entity.getUserId())
                .memoryId(entity.getMemoryId())
                .tagName(entity.getTagName())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .build();
    }
}
