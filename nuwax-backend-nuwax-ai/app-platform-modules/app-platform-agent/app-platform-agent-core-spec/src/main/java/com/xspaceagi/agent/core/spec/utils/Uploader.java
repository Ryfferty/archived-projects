package com.xspaceagi.agent.core.spec.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class Uploader {

    @Value("${cos.baseUrl}")
    private String baseUrl;

    @Value("${cos.secretId}")
    private String secretId;

    @Value("${cos.secretKey}")
    private String secretKey;

    public UploadResult upload(InputStream in, long length, String contentType, String fileKey, String originalFilename) {
        return null;
    }

    @Data
    public static class UploadResult {

        private String url;

        private String key;

        private String fileName;

        private String mimeType;

        private int size;

        private int width;

        private int height;
    }
}
