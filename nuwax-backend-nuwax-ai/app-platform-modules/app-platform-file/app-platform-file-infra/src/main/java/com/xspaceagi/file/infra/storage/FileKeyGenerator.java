package com.xspaceagi.file.infra.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * File Key Generator Utility
 */
public class FileKeyGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Generate file key
     * Format: /storageType/businessType/date/uuid.ext
     * Example: /s3/agent/20260417/a1b2c3d4.jpg
     *
     * @param fileName    Original file name
     * @param targetType  Business type
     * @param storageType Storage type
     * @return File key
     */
    public static String generate(String fileName, String targetType, String storageType) {
        String extension = extractExtension(fileName);
        String date = LocalDate.now().format(DATE_FORMATTER);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String businessType = targetType != null ? targetType.toLowerCase() : "default";

        return String.format("%s/%s/%s/%s%s", storageType, businessType, date, uuid, extension);
    }

    /**
     * Extract file extension
     *
     * @param fileName File name
     * @return Extension (including dot), or empty string if no extension
     */
    private static String extractExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }
}
