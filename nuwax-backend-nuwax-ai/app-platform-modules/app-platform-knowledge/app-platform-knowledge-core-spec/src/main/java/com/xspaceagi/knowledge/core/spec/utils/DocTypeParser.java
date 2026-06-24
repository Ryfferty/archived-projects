package com.xspaceagi.knowledge.core.spec.utils;

import com.xspaceagi.knowledge.core.spec.enums.DocEnum;

public class DocTypeParser {
    public static DocEnum getDocType(String fileURL) {
        int lastDotIndex = fileURL.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return null;
        }
        String extension = fileURL.substring(lastDotIndex + 1);
        switch (extension.toLowerCase()) {
            case "doc":
            case "docx":
                return DocEnum.WORD;
            case "pdf":
                return DocEnum.PDF;
            case "json":
                return DocEnum.JSON;
            case "txt":
                return DocEnum.TXT;
            default:
                return null;
        }
    }
}