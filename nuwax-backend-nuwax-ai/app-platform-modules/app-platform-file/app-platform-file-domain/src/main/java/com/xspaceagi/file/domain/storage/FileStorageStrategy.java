package com.xspaceagi.file.domain.storage;

import java.io.InputStream;

/**
 * 文件存储策略接口
 */
public interface FileStorageStrategy {

    /**
     * 上传文件
     *
     * @param inputStream 文件流
     * @param fileName    文件名
     * @param contentType 文件类型
     * @param targetType  业务类型
     * @return 文件key
     */
    String upload(InputStream inputStream, String fileName, String contentType, String targetType);

    /**
     * 下载文件
     *
     * @param fileKey 文件key
     * @return 文件流
     */
    InputStream download(String fileKey);

    /**
     * 删除文件
     *
     * @param fileKey 文件key
     * @return 是否成功
     */
    boolean delete(String fileKey);

    /**
     * 获取文件访问URL
     *
     * @param fileKey 文件key
     * @return 访问URL
     */
    String getFileUrl(String fileKey);

    /**
     * 生成签名URL（用于云存储）
     *
     * @param fileKey 文件key
     * @param expireSeconds 过期时间（秒）
     * @return 签名URL，如果不支持则返回null
     */
    default String generatePresignedUrl(String fileKey, int expireSeconds) {
        return null;
    }

    /**
     * 生成签名URL（用于云存储），支持指定下载文件名
     *
     * @param fileKey 文件key
     * @param expireSeconds 过期时间（秒）
     * @param fileName 下载时的文件名
     * @return 签名URL，如果不支持则返回null
     */
    default String generatePresignedUrl(String fileKey, int expireSeconds, String fileName) {
        return generatePresignedUrl(fileKey, expireSeconds);
    }

    /**
     * 获取存储类型
     *
     * @return 存储类型
     */
    String getStorageType();
}
