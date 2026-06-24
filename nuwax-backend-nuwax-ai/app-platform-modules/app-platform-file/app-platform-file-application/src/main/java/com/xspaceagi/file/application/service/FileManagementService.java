package com.xspaceagi.file.application.service;

import com.xspaceagi.file.domain.model.FileRecordDomain;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * File Management Service Interface
 */
public interface FileManagementService {

    /**
     * Upload file
     *
     * @param file           File to upload
     * @param tenantId       Tenant ID
     * @param userId         User ID
     * @param targetType     Target object type
     * @param targetId       Target object ID
     * @param metadata       Metadata
     * @param isAuthRequired Whether authentication is required
     * @return File record
     */
    FileRecordDomain uploadFile(MultipartFile file, Long tenantId, Long userId,
                                String targetType, Long targetId, String metadata, boolean isAuthRequired);

    /**
     * Download file
     *
     * @param fileKey File key
     * @return File input stream
     */
    InputStream downloadFile(String fileKey);

    /**
     * Delete file
     *
     * @param fileId File ID
     * @return Success status
     */
    boolean deleteFile(Long fileId);

    /**
     * Batch delete files
     *
     * @param fileIds List of file IDs
     * @return Success status
     */
    boolean batchDeleteFiles(List<Long> fileIds);

    /**
     * Get file record by ID
     *
     * @param fileId File ID
     * @return File record
     */
    FileRecordDomain getFileById(Long fileId);

    /**
     * Get file record by key
     *
     * @param fileKey File key
     * @return File record
     */
    FileRecordDomain getFileByKey(String fileKey);

    /**
     * List user files
     *
     * @param tenantId Tenant ID
     * @param userId   User ID
     * @return List of file records
     */
    List<FileRecordDomain> listUserFiles(Long tenantId, Long userId);

    /**
     * List target files
     *
     * @param tenantId   Tenant ID
     * @param targetType Target object type
     * @param targetId   Target object ID
     * @return List of file records
     */
    List<FileRecordDomain> listTargetFiles(Long tenantId, String targetType, Long targetId);

    /**
     * Get file access URL
     *
     * @param fileKey File key
     * @return Access URL
     */
    String getFileUrl(String fileKey);

    /**
     * Generate presigned URL (for cloud storage)
     *
     * @param expireSeconds Expiration time in seconds
     * @return Presigned URL
     */
    String generatePresignedUrl(String fileKey, int expireSeconds);

    /**
     * Generate presigned URL by storage type (for cloud storage, no database query needed)
     *
     * @param fileKey       File key
     * @param storageType   Storage type
     * @param expireSeconds Expiration time in seconds
     * @return Presigned URL
     */
    String generatePresignedUrlByType(String fileKey, String storageType, int expireSeconds, Integer download);
}
