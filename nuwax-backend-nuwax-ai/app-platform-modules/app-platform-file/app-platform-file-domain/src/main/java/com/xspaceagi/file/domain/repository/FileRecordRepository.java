package com.xspaceagi.file.domain.repository;

import com.xspaceagi.file.domain.model.FileRecordDomain;

import java.util.List;

/**
 * 文件记录仓储接口
 */
public interface FileRecordRepository {

    /**
     * 保存文件记录
     *
     * @param fileRecord 文件记录
     * @return 文件记录
     */
    FileRecordDomain save(FileRecordDomain fileRecord);

    /**
     * 根据ID查询文件记录
     *
     * @param id 文件ID
     * @return 文件记录
     */
    FileRecordDomain findById(Long id);

    /**
     * 根据文件key查询文件记录
     *
     * @param fileKey 文件key
     * @return 文件记录
     */
    FileRecordDomain findByFileKey(String fileKey);

    /**
     * 根据租户ID和用户ID查询文件列表
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return 文件列表
     */
    List<FileRecordDomain> findByTenantIdAndUserId(Long tenantId, Long userId);

    /**
     * 根据来源对象查询文件列表
     *
     * @param tenantId   租户ID
     * @param targetType 来源对象类型
     * @param targetId   来源对象ID
     * @return 文件列表
     */
    List<FileRecordDomain> findByTarget(Long tenantId, String targetType, Long targetId);

    /**
     * 根据存储类型查询文件列表
     *
     * @param tenantId    租户ID
     * @param storageType 存储类型
     * @return 文件列表
     */
    List<FileRecordDomain> findByStorageType(Long tenantId, String storageType);

    /**
     * 更新文件记录
     *
     * @param fileRecord 文件记录
     * @return 是否成功
     */
    boolean update(FileRecordDomain fileRecord);

    /**
     * 批量更新文件状态
     *
     * @param ids    文件ID列表
     * @param status 状态
     * @return 更新数量
     */
    int updateStatusByIds(List<Long> ids, String status);

    /**
     * 删除文件记录
     *
     * @param id 文件ID
     * @return 是否成功
     */
    boolean deleteById(Long id);
}
