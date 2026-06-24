package com.xspaceagi.file.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.file.infra.dao.entity.FileRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件记录Mapper
 *
 * @description 针对表【file_record(文件记录表)】的数据库操作Mapper
 * @Entity com.xspaceagi.file.infra.dao.entity.FileRecord
 */
public interface FileRecordMapper extends BaseMapper<FileRecord> {

    /**
     * 根据租户ID和用户ID查询文件列表
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return 文件列表
     */
    List<FileRecord> findByTenantIdAndUserId(@Param("tenantId") Long tenantId, @Param("userId") Long userId);

    /**
     * 根据来源对象查询文件列表
     *
     * @param tenantId   租户ID
     * @param targetType 来源对象类型
     * @param targetId   来源对象ID
     * @return 文件列表
     */
    List<FileRecord> findByTarget(@Param("tenantId") Long tenantId,
                                   @Param("targetType") String targetType,
                                   @Param("targetId") Long targetId);

    /**
     * 根据文件key查询文件
     *
     * @param fileKey 文件key
     * @return 文件记录
     */
    FileRecord findByFileKey(@Param("fileKey") String fileKey);

    /**
     * 根据存储类型查询文件列表
     *
     * @param tenantId    租户ID
     * @param storageType 存储类型
     * @return 文件列表
     */
    List<FileRecord> findByStorageType(@Param("tenantId") Long tenantId, @Param("storageType") String storageType);

    /**
     * 批量更新文件状态
     *
     * @param ids    文件ID列表
     * @param status 状态
     * @return 更新数量
     */
    int updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") String status);
}
