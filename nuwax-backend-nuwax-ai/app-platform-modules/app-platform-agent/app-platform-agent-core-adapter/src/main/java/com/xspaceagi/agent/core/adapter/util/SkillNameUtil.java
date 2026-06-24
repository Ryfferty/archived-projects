package com.xspaceagi.agent.core.adapter.util;

import com.xspaceagi.agent.core.adapter.dto.SkillConfigDto;
import com.xspaceagi.agent.core.adapter.dto.SkillFileDto;
import com.xspaceagi.agent.core.spec.utils.MarkdownExtractUtil;
import com.xspaceagi.file.sdk.IFileAccessService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class SkillNameUtil {

    private SkillNameUtil() {
    }

    public static void backfillName(SkillConfigDto skillConfigDto, IFileAccessService iFileAccessService) {
        if (skillConfigDto == null) {
            return;
        }
        String fallbackName = StringUtils.defaultIfBlank(skillConfigDto.getEnName(), skillConfigDto.getName());
        var files = skillConfigDto.getFiles();
        if (CollectionUtils.isEmpty(files)) {
            if (StringUtils.isNotBlank(fallbackName)) {
                skillConfigDto.setEnName(fallbackName);
            }
            return;
        }
        for (SkillFileDto file : files) {
            if (file == null || Boolean.TRUE.equals(file.getIsDir()) || !"SKILL.MD".equalsIgnoreCase(file.getName())) {
                continue;
            }
            String skillMdContent = resolveSkillFileContent(file, iFileAccessService);
            if (StringUtils.isBlank(skillMdContent)) {
                continue;
            }
            String skillNameFromMd = MarkdownExtractUtil.extractFieldValue(skillMdContent, "name");
            if (StringUtils.isNotBlank(skillNameFromMd)) {
                skillConfigDto.setEnName(skillNameFromMd);
                return;
            }
        }
        if (StringUtils.isNotBlank(fallbackName)) {
            skillConfigDto.setEnName(fallbackName);
        }
    }

    private static String resolveSkillFileContent(SkillFileDto skillFileDto, IFileAccessService iFileAccessService) {
        if (skillFileDto == null) {
            return null;
        }
        if (StringUtils.isNotBlank(skillFileDto.getFileProxyUrl()) && iFileAccessService != null) {
            String content = downloadSkillFileContent(skillFileDto.getFileProxyUrl(), iFileAccessService);
            if (StringUtils.isNotBlank(content)) {
                return content;
            }
        }
        return skillFileDto.getContents();
    }

    private static String downloadSkillFileContent(String fileProxyUrl, IFileAccessService iFileAccessService) {
        try {
            String fileUrlWithAk = iFileAccessService.getFileUrlWithAk(fileProxyUrl, true);
            URL fileUrl = new URL(fileUrlWithAk);
            try (InputStream in = fileUrl.openStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                return new String(out.toByteArray(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
