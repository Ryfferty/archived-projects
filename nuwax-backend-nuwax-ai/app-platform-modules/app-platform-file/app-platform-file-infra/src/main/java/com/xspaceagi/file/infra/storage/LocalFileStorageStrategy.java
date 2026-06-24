package com.xspaceagi.file.infra.storage;

import com.xspaceagi.file.domain.storage.FileStorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Local File Storage Implementation
 */
@Slf4j
@Component
public class LocalFileStorageStrategy implements FileStorageStrategy {

    @Value("${local.upload.folder:}")
    private String uploadFolder;

    @Value("${file.uploadFolder:/tmp/uploads}")
    private String oldUploadFolder;

    @Override
    public String upload(InputStream inputStream, String fileName, String contentType, String targetType) {
        try {
            String fileKey = FileKeyGenerator.generate(fileName, targetType, getStorageType());
            Path targetPath = Paths.get(StringUtils.isBlank(uploadFolder) ? oldUploadFolder : uploadFolder, fileKey);

            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

            log.info("File uploaded successfully: {}", fileKey);
            return fileKey;
        } catch (IOException e) {
            log.error("File upload failed", e);
            throw new RuntimeException("File upload failed", e);
        }
    }

    @Override
    public InputStream download(String fileKey) {
        try {
            Path filePath = Paths.get(StringUtils.isBlank(uploadFolder) ? oldUploadFolder : uploadFolder, fileKey);
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("File not found: " + fileKey);
            }
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            log.error("File download failed: {}", fileKey, e);
            throw new RuntimeException("File download failed", e);
        }
    }

    @Override
    public boolean delete(String fileKey) {
        try {
            Path filePath = Paths.get(StringUtils.isBlank(uploadFolder) ? oldUploadFolder : uploadFolder, fileKey);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("File deletion failed: {}", fileKey, e);
            return false;
        }
    }

    @Override
    public String getFileUrl(String fileKey) {
        return fileKey;
    }

    @Override
    public String getStorageType() {
        return "local";
    }
}
