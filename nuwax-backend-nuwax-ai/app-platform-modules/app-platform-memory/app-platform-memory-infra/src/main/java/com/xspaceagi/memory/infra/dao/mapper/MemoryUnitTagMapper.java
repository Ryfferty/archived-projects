package com.xspaceagi.memory.infra.dao.mapper;

import com.xspaceagi.memory.infra.dao.entity.MemoryUnitTag;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 记忆单元标签Mapper
 * @description 针对表【memory_unit_tag(记忆单元标签表)】的数据库操作Mapper
 * @createDate 2026-03-06
 * @Entity com.xspaceagi.memory.infra.dao.entity.MemoryUnitTag
 */
public interface MemoryUnitTagMapper extends BaseMapper<MemoryUnitTag> {

    /**
     * 根据记忆ID查询标签列表
     *
     * @param memoryId 记忆ID
     * @return 标签列表
     */
    List<MemoryUnitTag> findByMemoryId(@Param("memoryId") Long memoryId);

    /**
     * 根据用户ID查询标签列表
     *
     * @param userId 用户ID
     * @return 标签列表
     */
    List<MemoryUnitTag> findByUserId(@Param("userId") Long userId);

    /**
     * 根据标签名称查询标签列表
     *
     * @param tagName 标签名称
     * @return 标签列表
     */
    List<MemoryUnitTag> findByTagName(@Param("tagName") String tagName);

    /**
     * 根据记忆ID删除标签
     *
     * @param memoryId 记忆ID
     * @return 删除数量
     */
    int deleteByMemoryId(@Param("memoryId") Long memoryId);

    /**
     * 批量插入标签
     *
     * @param tags 标签列表
     * @return 插入数量
     */
    int batchInsert(@Param("tags") List<MemoryUnitTag> tags);
}
