package com.xspaceagi.agent.core.application.service;

import com.alibaba.fastjson2.JSON;
import com.xspaceagi.agent.core.adapter.application.AgentWorkspaceApplicationService;
import com.xspaceagi.agent.core.adapter.application.PublishApplicationService;
import com.xspaceagi.agent.core.adapter.constant.SkillFileFormatConstants;
import com.xspaceagi.agent.core.adapter.dto.*;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.SkillConfig;
import com.xspaceagi.agent.core.domain.service.SkillDomainService;
import com.xspaceagi.agent.core.infra.rpc.SkillFileClient;
import com.xspaceagi.agent.core.adapter.util.SkillNameUtil;
import com.xspaceagi.agent.core.spec.utils.FileTypeUtils;
import com.xspaceagi.agent.core.spec.utils.MarkdownExtractUtil;
import com.xspaceagi.file.sdk.IFileAccessService;
import com.xspaceagi.system.spec.enums.ErrorCodeEnum;
import com.xspaceagi.system.spec.exception.BizException;
import com.xspaceagi.system.spec.exception.BizExceptionCodeEnum;
import com.xspaceagi.system.spec.file.InMemoryMultipartFile;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class AgentWorkspaceApplicationServiceImpl implements AgentWorkspaceApplicationService {
    @Resource
    private SkillFileClient skillFileClient;
    @Resource
    private SkillDomainService skillDomainService;
    @Resource
    private PublishApplicationService publishApplicationService;
    @Resource
    private IFileAccessService iFileAccessService;

    @Override
    public void createWorkspace(CreateWorkspaceDto createWorkspaceDto) {
        log.info("[createWorkspace] userId={} cId={} skillIds={}, subagents.size={}  创建工作空间开始",
                createWorkspaceDto.getUserId(), createWorkspaceDto.getCId(), createWorkspaceDto.getSkillIds(), CollectionUtils.size(createWorkspaceDto.getSubagents()));
        Long userId = createWorkspaceDto.getUserId();
        Long cId = createWorkspaceDto.getCId();
        List<Long> skillIds = createWorkspaceDto.getSkillIds();
        List<SubagentDto> subagents = createWorkspaceDto.getSubagents();

        if (userId == null || cId == null) {
            throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentRequiredParamEmpty);
        }

        List<SkillConfigDto> toPushSkills = new ArrayList<>();
        List<String> skillUrls = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(skillIds)) {
            List<SkillConfig> skillConfigs = skillDomainService.listByIds(skillIds);
            if (CollectionUtils.isNotEmpty(skillConfigs)) {
                for (SkillConfig skillConfig : skillConfigs) {
                    Published.PublishStatus publishStatus = skillConfig.getPublishStatus();
                    if (publishStatus != Published.PublishStatus.Published) {
                        log.warn("[createWorkspace] userId={} cId={} skillId={} 技能未发布，跳过", userId, cId, skillConfig.getId());
                        continue;
                    }

                    PublishedDto publishedDto = publishApplicationService.queryPublished(Published.TargetType.Skill, skillConfig.getId(), true);
                    if (publishedDto == null) {
                        log.warn("[createWorkspace] userId={} cId={} skillId={} 技能无发布信息，跳过", userId, cId, skillConfig.getId());
                        continue;
                    }
                    String config = publishedDto.getConfig();
                    SkillConfigDto dto = parseSkillConfig(config);
                    SkillNameUtil.backfillName(dto, iFileAccessService);

                    if (isV2Config(dto)) {
                        if (StringUtils.isNotBlank(dto.getZipFileUrl())) {
                            skillUrls.add(iFileAccessService.getFileUrlWithAk(dto.getZipFileUrl(), true));
                        }
                        continue;
                    }

                    if (CollectionUtils.isEmpty(dto.getFiles())) {
                        log.warn("[createWorkspace] userId={} cId={} skillId={} 技能无文件，跳过", userId, cId, skillConfig.getId());
                        continue;
                    }

                    log.info("[createWorkspace] userId={} cId={} skillId={}, skillName={} 技能打包开始", userId, cId, dto.getId(), dto.getName());
                    toPushSkills.add(dto);
                }
            }
        }

        MultipartFile zipFile = null;
        if (CollectionUtils.isNotEmpty(toPushSkills) || CollectionUtils.isNotEmpty(subagents)) {
            zipFile = buildZip(toPushSkills, subagents);
        }

        Map<String, Object> result = null;
        try {
            result = skillFileClient.createWorkSpaceV2(userId, cId, zipFile, skillUrls);
        } catch (Exception e) {
            log.warn("[createWorkspace] userId={} cId={} 调用 createWorkSpaceV2 异常，准备回退到 createWorkSpace", userId, cId, e);
        }

        boolean v2Success = isSuccess(result);
        if (!v2Success) {
            String message = result == null ? "response is null" : String.valueOf(result.getOrDefault("message", "createWorkSpaceV2 failed"));
            log.warn("[createWorkspace] userId={} cId={} createWorkSpaceV2失败，准备回退。message={}", userId, cId, message);

            MultipartFile fallbackZipFile = buildZipWithSkillUrls(zipFile, skillUrls, false);
            result = skillFileClient.createWorkSpace(userId, cId, fallbackZipFile);
        }

        if (result == null) {
            throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentWorkspaceCreateFailed);
        }

        Object successObj = result.get("success");
        if (successObj instanceof Boolean && !(Boolean) successObj) {
            String message = result.getOrDefault("message", "创建工作空间失败").toString();
            log.error("[createWorkspace] userId={} cId={} 创建工作空间失败, message={}", userId, cId, message);
            throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.validationFailedWithDetail, message);
        }
        log.info("[createWorkspace] userId={} cId={} 创建工作空间成功", userId, cId);
    }

    @Override
    public void addSkillsToWorkspace(AddSkillsToWorkspaceDto addSkillsToWorkspaceDto) {
        Long userId = addSkillsToWorkspaceDto.getUserId();
        Long cId = addSkillsToWorkspaceDto.getCId();
        List<SkillConfigDto> oldSkills = new ArrayList<>();
        List<String> skillUrls = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(addSkillsToWorkspaceDto.getSkillConfigs())) {
            for (SkillConfigDto skillConfig : addSkillsToWorkspaceDto.getSkillConfigs()) {
                if (isV2Config(skillConfig)) {
                    if (StringUtils.isNotBlank(skillConfig.getZipFileUrl())) {
                        skillUrls.add(iFileAccessService.getFileUrlWithAk(skillConfig.getZipFileUrl(), true));
                    }
                } else {
                    SkillNameUtil.backfillName(skillConfig, iFileAccessService);
                    oldSkills.add(skillConfig);
                }
            }
        }
        appendDynamicAddLockFiles(oldSkills);
        MultipartFile baseZipFile = buildZip(oldSkills, Collections.emptyList());

        Map<String, Object> result = null;
        try {
            result = skillFileClient.pushSkillsToWorkspaceV2(userId, cId, baseZipFile, skillUrls);
        } catch (Exception e) {
            log.warn("[addSkills] userId={} cId={} 调用 pushSkillsToWorkspaceV2 异常，准备回退到 pushSkillsToWorkspace", userId, cId, e);
        }
        // 回退老版本接口
        if (!isSuccess(result)) {
            String message = result == null ? "response is null" : String.valueOf(result.getOrDefault("message", "pushSkillsToWorkspaceV2 failed"));
            log.warn("[addSkills] userId={} cId={} pushSkillsToWorkspaceV2失败，准备回退。message={}", userId, cId, message);

            MultipartFile zipFile = buildZipWithSkillUrls(baseZipFile, skillUrls, true);
            if (zipFile == null) {
                throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentSkillZipPackFailed);
            }
            result = skillFileClient.pushSkillsToWorkspace(userId, cId, zipFile);
        }

        if (result == null) {
            log.error("[addSkills] userId={} cId={} 推送技能文件失败，响应为空", userId, cId);
            throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentSkillFilePushFailed);
        }
        Object successObj = result.get("success");
        if (successObj instanceof Boolean && !(Boolean) successObj) {
            String message = result.getOrDefault("message", "推送技能文件失败").toString();
            log.error("[addSkills] userId={} cId={} 推送技能文件失败, message={}", userId, cId, message);
            throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentSkillFilePushFailed, message);
        }

        log.info("[addSkills] userId={} cId={} 动态增加技能完成", userId, cId);
    }

    /**
     * 构建动态增加技能的锁标志文件
     * 写入技能文件夹根目录下，用于标识该技能为动态增加
     */
    private SkillFileDto buildDynamicAddLockFile() {
        SkillFileDto lockFile = new SkillFileDto();
        lockFile.setName(".dynamic_add.lock");
        lockFile.setContents("dynamic_add\n");
        lockFile.setOperation("create");
        lockFile.setIsDir(false);
        return lockFile;
    }

    private void appendDynamicAddLockFiles(List<SkillConfigDto> skills) {
        if (CollectionUtils.isEmpty(skills)) {
            return;
        }
        for (SkillConfigDto skill : skills) {
            if (skill == null || CollectionUtils.isEmpty(skill.getFiles())) {
                continue;
            }
            boolean hasLockFile = false;
            for (SkillFileDto file : skill.getFiles()) {
                if (file != null && ".dynamic_add.lock".equals(file.getName())) {
                    hasLockFile = true;
                    break;
                }
            }
            if (!hasLockFile) {
                skill.getFiles().add(buildDynamicAddLockFile());
            }
        }
    }

    //
    // ------------------ 以下是private方法 -----------------------
    //

    /**
     * 将skills和subagents打包成 zip
     */
    private MultipartFile buildZip(List<SkillConfigDto> skills, List<SubagentDto> subagents) {
        if (CollectionUtils.isEmpty(skills) && CollectionUtils.isEmpty(subagents)) {
            return null;
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zos = new ZipOutputStream(baos, StandardCharsets.UTF_8)) {

            // 显式创建 skills 根目录，避免下游只按目录 entry 检查而认为没有 skills 目录
            ZipEntry skillsRoot = new ZipEntry("skills/");
            zos.putNextEntry(skillsRoot);
            zos.closeEntry();

            // 显式创建 agents 根目录
            ZipEntry agentsRoot = new ZipEntry("agents/");
            zos.putNextEntry(agentsRoot);
            zos.closeEntry();

            // 用于跟踪已添加的条目，避免重复添加
            Set<String> addedEntries = new HashSet<>();
            addedEntries.add("skills/");
            addedEntries.add("agents/");

            // 处理 skills
            if (CollectionUtils.isNotEmpty(skills)) {
                for (SkillConfigDto skill : skills) {
                    if (skill == null || CollectionUtils.isEmpty(skill.getFiles())) {
                        continue;
                    }

                    String skillName = StringUtils.isNotBlank(skill.getEnName()) ? skill.getEnName() : skill.getName();
                    if (StringUtils.isBlank(skillName)) {
                        continue;
                    }

                    String skillDir = "skills/" + skillName + "/";

                    // 确保技能目录被创建
                    if (!addedEntries.contains(skillDir)) {
                        ZipEntry skillDirEntry = new ZipEntry(skillDir);
                        zos.putNextEntry(skillDirEntry);
                        zos.closeEntry();
                        addedEntries.add(skillDir);
                    }

                    for (SkillFileDto fileDto : skill.getFiles()) {
                        if (fileDto == null || fileDto.getName() == null || fileDto.getName().isBlank()) {
                            continue;
                        }

                        String fileName = fileDto.getName();
                        // 规范化文件名：去除前导斜杠，避免双斜杠
                        if (fileName.startsWith("/")) {
                            fileName = fileName.substring(1);
                        }

                        String entryName = skillDir + fileName;

                        // 如果是目录，确保路径以/结尾
                        if (Boolean.TRUE.equals(fileDto.getIsDir())) {
                            if (!entryName.endsWith("/")) {
                                entryName = entryName + "/";
                            }
                            // 跳过已添加的目录
                            if (addedEntries.contains(entryName)) {
                                continue;
                            }
                            addedEntries.add(entryName);
                            ZipEntry entry = new ZipEntry(entryName);
                            zos.putNextEntry(entry);
                            zos.closeEntry();
                        } else {
                            // 对于文件，先确保所有父目录都被创建
                            ensureParentDirectories(zos, entryName, skillDir, addedEntries);

                            // 跳过已添加的文件
                            if (addedEntries.contains(entryName)) {
                                continue;
                            }
                            addedEntries.add(entryName);
                            ZipEntry entry = new ZipEntry(entryName);
                            zos.putNextEntry(entry);

                            String contents = fileDto.getContents();
                            if (contents != null) {
                                byte[] bytes = getFileBytes(contents, fileDto.getName());
                                zos.write(bytes);
                            }

                            zos.closeEntry();
                        }
                    }
                }
            }

            // 处理 subagents，每个 SubagentDto 转换成一个 markdown 文件
            if (!CollectionUtils.isEmpty(subagents)) {
                for (SubagentDto subagent : subagents) {
                    if (subagent == null || StringUtils.isBlank(subagent.getContent())) {
                        continue;
                    }

                    String fileName = StringUtils.isNotBlank(subagent.getName())
                            ? subagent.getName()
                            : MarkdownExtractUtil.extractFieldValue(subagent.getContent(), "name");
                    if (StringUtils.isBlank(fileName)) {
                        continue;
                    }
                    // 确保文件名以 .md 结尾
                    if (!fileName.toLowerCase().endsWith(".md")) {
                        fileName = fileName + ".md";
                    }
                    String entryName = "agents/" + fileName;

                    // 跳过已添加的文件
                    if (addedEntries.contains(entryName)) {
                        continue;
                    }
                    addedEntries.add(entryName);

                    ZipEntry entry = new ZipEntry(entryName);
                    zos.putNextEntry(entry);

                    String content = subagent.getContent();
                    if (content != null) {
                        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                        zos.write(bytes);
                    }

                    zos.closeEntry();
                }
            }

            zos.finish();
            byte[] zipBytes = baos.toByteArray();

            if (zipBytes.length == 0) {
                return null;
            }

            return new InMemoryMultipartFile("file", "skills.zip", "application/zip", zipBytes);
        } catch (IOException e) {
            log.error("[skill/agent] 打包skill/agent zip失败", e);
            throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentSkillAgentPackFailed);
        }
    }

    /**
     * 获取文件的字节数组
     * 如果是二进制文件且内容为 base64 编码，则解码；否则直接转换为字节数组
     */
    private byte[] getFileBytes(String contents, String fileName) {
        if (contents == null || contents.isBlank()) {
            return new byte[0];
        }

        // 如果是文本文件，直接转换为字节数组
        if (FileTypeUtils.isTextFile(fileName)) {
            return contents.getBytes(StandardCharsets.UTF_8);
        }

        // 非文本文件（二进制），尝试解码 base64
        try {
            // 尝试解码 base64
            return Base64.getDecoder().decode(contents);
        } catch (IllegalArgumentException e) {
            // 如果不是有效的 base64，则按文本处理（兼容旧数据）
            log.warn("File {} is not valid base64, treating as text", fileName);
            return contents.getBytes(StandardCharsets.UTF_8);
        }
    }

    /**
     * 确保文件的所有父目录都被创建
     *
     * @param zos          ZipOutputStream
     * @param filePath     文件路径，如 "skills/skillName/abc/xxx.py"
     * @param baseDir      基础目录，如 "skills/skillName/"
     * @param addedEntries 已添加的条目集合
     */
    private void ensureParentDirectories(ZipOutputStream zos, String filePath, String baseDir, Set<String> addedEntries) throws IOException {
        // 移除基础目录前缀，获取相对路径
        String relativePath = filePath;
        if (filePath.startsWith(baseDir)) {
            relativePath = filePath.substring(baseDir.length());
        }

        // 如果路径中没有目录分隔符，说明文件在根目录下，不需要创建父目录
        if (!relativePath.contains("/")) {
            return;
        }

        // 提取所有父目录路径
        String[] parts = relativePath.split("/");
        StringBuilder currentPath = new StringBuilder(baseDir);

        // 遍历所有目录部分（除了最后一个文件名）
        for (int i = 0; i < parts.length - 1; i++) {
            if (parts[i] == null || parts[i].isBlank()) {
                continue;
            }
            currentPath.append(parts[i]).append("/");
            String dirPath = currentPath.toString();

            // 如果目录还未添加，则创建它
            if (!addedEntries.contains(dirPath)) {
                ZipEntry dirEntry = new ZipEntry(dirPath);
                zos.putNextEntry(dirEntry);
                zos.closeEntry();
                addedEntries.add(dirPath);
            }
        }
    }

    private boolean isSuccess(Map<String, Object> result) {
        if (result == null) {
            return false;
        }
        Object successObj = result.get("success");
        return !(successObj instanceof Boolean) || (Boolean) successObj;
    }

    /**
     * 回退到 create-workspace 时，将 skillUrls 对应的技能文件下载解压后合并到 zip 的 skills/ 目录下
     */
    private MultipartFile buildZipWithSkillUrls(MultipartFile originalZipFile, List<String> skillUrls, boolean appendDynamicLockFile) {
        if (CollectionUtils.isEmpty(skillUrls)) {
            return originalZipFile;
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zos = new ZipOutputStream(baos, StandardCharsets.UTF_8)) {
            Set<String> addedEntries = new HashSet<>();
            addDirectoryEntry(zos, "skills/", addedEntries);
            addDirectoryEntry(zos, "agents/", addedEntries);

            if (originalZipFile != null) {
                copyZipToOutput(originalZipFile.getBytes(), zos, addedEntries);
            }

            for (String skillUrl : skillUrls) {
                if (StringUtils.isBlank(skillUrl)) {
                    continue;
                }
                try (InputStream inputStream = new URL(skillUrl).openStream()) {
                    byte[] zipBytes = inputStream.readAllBytes();
                    copySkillZipToSkillsRoot(zipBytes, zos, addedEntries, appendDynamicLockFile);
                } catch (Exception e) {
                    log.error("[createWorkspace] 回退打包失败，下载或解压 skillUrl 异常, skillUrl={}", skillUrl, e);
                    throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentSkillZipPackFailed);
                }
            }

            zos.finish();
            byte[] zipBytes = baos.toByteArray();
            if (zipBytes.length == 0) {
                return null;
            }
            return new InMemoryMultipartFile("file", "skills.zip", "application/zip", zipBytes);
        } catch (IOException e) {
            log.error("[createWorkspace] 回退打包失败", e);
            throw BizException.of(ErrorCodeEnum.INVALID_PARAM, BizExceptionCodeEnum.agentSkillZipPackFailed);
        }
    }

    private void copyZipToOutput(byte[] sourceZipBytes, ZipOutputStream zos, Set<String> addedEntries) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new java.io.ByteArrayInputStream(sourceZipBytes), StandardCharsets.UTF_8)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = normalizeZipPath(entry.getName());
                if (StringUtils.isBlank(entryName)) {
                    zis.closeEntry();
                    continue;
                }
                if (entry.isDirectory()) {
                    addDirectoryEntry(zos, entryName, addedEntries);
                } else {
                    byte[] data = zis.readAllBytes();
                    addFileEntry(zos, entryName, data, addedEntries);
                }
                zis.closeEntry();
            }
        }
    }

    private void copySkillZipToSkillsRoot(byte[] sourceZipBytes, ZipOutputStream zos, Set<String> addedEntries, boolean appendDynamicLockFile) throws IOException {
        Set<String> skillDirs = new HashSet<>();
        try (ZipInputStream zis = new ZipInputStream(new java.io.ByteArrayInputStream(sourceZipBytes), StandardCharsets.UTF_8)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String rawName = normalizeZipPath(entry.getName());
                if (StringUtils.isBlank(rawName)) {
                    zis.closeEntry();
                    continue;
                }
                String targetName = rawName.startsWith("skills/") ? rawName : "skills/" + rawName;
                String skillDir = extractSkillDir(targetName);
                if (StringUtils.isNotBlank(skillDir)) {
                    skillDirs.add(skillDir);
                }
                if (entry.isDirectory()) {
                    addDirectoryEntry(zos, targetName, addedEntries);
                } else {
                    byte[] data = zis.readAllBytes();
                    addFileEntry(zos, targetName, data, addedEntries);
                }
                zis.closeEntry();
            }
        }
        if (appendDynamicLockFile) {
            byte[] lockFileBytes = "dynamic_add\n".getBytes(StandardCharsets.UTF_8);
            for (String skillDir : skillDirs) {
                addFileEntry(zos, skillDir + ".dynamic_add.lock", lockFileBytes, addedEntries);
            }
        }
    }

    private void addDirectoryEntry(ZipOutputStream zos, String dirName, Set<String> addedEntries) throws IOException {
        String normalizedDir = normalizeDirectoryPath(dirName);
        if (StringUtils.isBlank(normalizedDir) || addedEntries.contains(normalizedDir)) {
            return;
        }
        ZipEntry entry = new ZipEntry(normalizedDir);
        zos.putNextEntry(entry);
        zos.closeEntry();
        addedEntries.add(normalizedDir);
    }

    private void addFileEntry(ZipOutputStream zos, String entryName, byte[] data, Set<String> addedEntries) throws IOException {
        String normalizedPath = normalizeZipPath(entryName);
        if (StringUtils.isBlank(normalizedPath) || addedEntries.contains(normalizedPath)) {
            return;
        }
        ensureParentDirectories(zos, normalizedPath, addedEntries);
        ZipEntry entry = new ZipEntry(normalizedPath);
        zos.putNextEntry(entry);
        if (data != null && data.length > 0) {
            zos.write(data);
        }
        zos.closeEntry();
        addedEntries.add(normalizedPath);
    }

    private void ensureParentDirectories(ZipOutputStream zos, String filePath, Set<String> addedEntries) throws IOException {
        String normalizedPath = normalizeZipPath(filePath);
        if (StringUtils.isBlank(normalizedPath) || !normalizedPath.contains("/")) {
            return;
        }
        int index = normalizedPath.lastIndexOf('/');
        if (index <= 0) {
            return;
        }
        String[] parts = normalizedPath.substring(0, index).split("/");
        StringBuilder pathBuilder = new StringBuilder();
        for (String part : parts) {
            if (StringUtils.isBlank(part)) {
                continue;
            }
            pathBuilder.append(part).append("/");
            addDirectoryEntry(zos, pathBuilder.toString(), addedEntries);
        }
    }

    private String normalizeZipPath(String entryName) {
        if (entryName == null) {
            return "";
        }
        String normalized = entryName.replace("\\", "/");
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        return normalized;
    }

    private String normalizeDirectoryPath(String dirName) {
        String normalized = normalizeZipPath(dirName);
        if (StringUtils.isBlank(normalized)) {
            return normalized;
        }
        return normalized.endsWith("/") ? normalized : normalized + "/";
    }

    private String extractSkillDir(String targetName) {
        String normalized = normalizeZipPath(targetName);
        if (StringUtils.isBlank(normalized) || !normalized.startsWith("skills/")) {
            return null;
        }
        String relative = normalized.substring("skills/".length());
        if (StringUtils.isBlank(relative)) {
            return null;
        }
        int index = relative.indexOf('/');
        if (index < 0) {
            return null;
        }
        String skillName = relative.substring(0, index);
        if (StringUtils.isBlank(skillName)) {
            return null;
        }
        return "skills/" + skillName + "/";
    }

    private SkillConfigDto parseSkillConfig(String config) {
        if (StringUtils.isBlank(config)) {
            return new SkillConfigDto();
        }
        try {
            SkillPublishedConfigDto publishedConfig = JSON.parseObject(config, SkillPublishedConfigDto.class);
            if (publishedConfig != null
                    && (SkillFileFormatConstants.SKILL_FILES_V2.equals(publishedConfig.getFormat()) || StringUtils.isNotBlank(publishedConfig.getZipFileUrl()))) {
                SkillConfigDto dto = new SkillConfigDto();
                dto.setId(publishedConfig.getId());
                dto.setName(publishedConfig.getName());
                dto.setDescription(publishedConfig.getDescription());
                dto.setIcon(publishedConfig.getIcon());
                dto.setFiles(publishedConfig.getFiles());
                dto.setZipFileUrl(publishedConfig.getZipFileUrl());
                return dto;
            }
        } catch (Exception e) {
            log.debug("parse skill config as v2 failed", e);
        }
        return JSON.parseObject(config, SkillConfigDto.class);
    }

    private boolean isV2Config(SkillConfigDto skillConfig) {
        return skillConfig != null && StringUtils.isNotBlank(skillConfig.getZipFileUrl());
    }

}
