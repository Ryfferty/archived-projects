package com.xspaceagi.file.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xspaceagi.file.domain.model.FileRecordDomain;
import com.xspaceagi.file.domain.repository.FileRecordRepository;
import com.xspaceagi.file.infra.dao.entity.FileRecord;
import com.xspaceagi.file.infra.dao.mapper.FileRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件记录仓储实现
 */
@Repository
@RequiredArgsConstructor
public class FileRecordRepositoryImpl implements FileRecordRepository {

    private final FileRecordMapper fileRecordMapper;

    @Override
    public FileRecordDomain save(FileRecordDomain fileRecord) {
        FileRecord entity = toEntity(fileRecord);
        fileRecordMapper.insert(entity);
        return toDomain(entity);
    }

    @Override
    public FileRecordDomain findById(Long id) {
        FileRecord entity = fileRecordMapper.selectById(id);
        return entity != null ? toDomain(entity) : null;
    }

    @Override
    public FileRecordDomain findByFileKey(String fileKey) {
        FileRecord entity = fileRecordMapper.findByFileKey(fileKey);
        return entity != null ? toDomain(entity) : null;
    }

    @Override
    public List<FileRecordDomain> findByTenantIdAndUserId(Long tenantId, Long userId) {
        List<FileRecord> entities = fileRecordMapper.findByTenantIdAndUserId(tenantId, userId);
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<FileRecordDomain> findByTarget(Long tenantId, String targetType, Long targetId) {
        List<FileRecord> entities = fileRecordMapper.findByTarget(tenantId, targetType, targetId);
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<FileRecordDomain> findByStorageType(Long tenantId, String storageType) {
        List<FileRecord> entities = fileRecordMapper.findByStorageType(tenantId, storageType);
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean update(FileRecordDomain fileRecord) {
        FileRecord entity = toEntity(fileRecord);
        return fileRecordMapper.updateById(entity) > 0;
    }

    @Override
    public int updateStatusByIds(List<Long> ids, String status) {
        return fileRecordMapper.updateStatusByIds(ids, status);
    }

    @Override
    public boolean deleteById(Long id) {
        return fileRecordMapper.deleteById(id) > 0;
    }

    private FileRecordDomain toDomain(FileRecord entity) {
        FileRecordDomain domain = new FileRecordDomain();
        BeanUtils.copyProperties(entity, domain);
        return domain;
    }

    private FileRecord toEntity(FileRecordDomain domain) {
        FileRecord entity = new FileRecord();
        BeanUtils.copyProperties(domain, entity);
        return entity;
    }
}
