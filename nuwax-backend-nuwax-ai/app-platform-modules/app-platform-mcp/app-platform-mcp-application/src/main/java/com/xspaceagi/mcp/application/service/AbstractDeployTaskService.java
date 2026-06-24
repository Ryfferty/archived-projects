package com.xspaceagi.mcp.application.service;

import com.alibaba.fastjson2.JSON;
import com.xspaceagi.mcp.adapter.domain.McpConfigDomainService;
import com.xspaceagi.mcp.adapter.repository.entity.McpConfig;
import com.xspaceagi.mcp.infra.client.McpAsyncClientWrapper;
import com.xspaceagi.mcp.infra.rpc.McpDeployRpcService;
import com.xspaceagi.mcp.sdk.dto.*;
import com.xspaceagi.mcp.sdk.enums.DeployStatusEnum;
import com.xspaceagi.mcp.sdk.enums.McpDataTypeEnum;
import com.xspaceagi.system.spec.utils.MD5;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractDeployTaskService {

    @Resource
    private McpDeployRpcService mcpDeployRpcService;

    @Resource
    private McpConfigDomainService mcpConfigDomainService;

    protected void updateAndSaveMcpConfig(Long id, String serverConfig, boolean isSSE, Date modified) {
        McpConfigDto mcpConfigDto = new McpConfigDto();
        mcpConfigDto.setServerConfig(serverConfig);
        McpAsyncClientWrapper mcpAsyncClientWrapper = null;
        try {
            if (isSSE) {
                mcpAsyncClientWrapper = mcpDeployRpcService.getMcpAsyncClientForSSE(String.valueOf(id), MD5.MD5Encode(serverConfig), serverConfig).block();
            } else {
                mcpAsyncClientWrapper = mcpDeployRpcService.getMcpAsyncClient(String.valueOf(id), MD5.MD5Encode(serverConfig), serverConfig).block();
            }
            McpSchema.ListToolsResult toolsResult = mcpAsyncClientWrapper.getClient().listTools().timeout(Duration.ofSeconds(30)).block();
            if (toolsResult.tools() != null) {
                mcpConfigDto.setTools(new ArrayList<>());
                for (McpSchema.Tool tool : toolsResult.tools()) {
                    McpToolDto mcpToolDto = new McpToolDto();
                    mcpToolDto.setName(tool.name());
                    mcpToolDto.setDescription(tool.description());
                    mcpToolDto.setInputArgs(convertInputSchemaToInputArgs(tool.inputSchema().properties(), tool.inputSchema().required()));
                    mcpToolDto.setJsonSchema(JSON.toJSONString(tool.inputSchema()));
                    mcpConfigDto.getTools().add(mcpToolDto);
                }
            }
            try {
                //数据量可能比较大，后续优化
                McpSchema.ListResourcesResult resourcesResult = mcpAsyncClientWrapper.getClient().listResources().timeout(Duration.ofSeconds(30)).block();
                if (resourcesResult != null) {
                    mcpConfigDto.setResources(new ArrayList<>());
                    for (McpSchema.Resource resource : resourcesResult.resources()) {
                        McpResourceDto mcpResourceDto = new McpResourceDto();
                        mcpResourceDto.setUri(resource.uri());
                        mcpResourceDto.setName(resource.name());
                        mcpResourceDto.setDescription(resource.description());
                        mcpResourceDto.setMimeType(resource.mimeType());
                        mcpResourceDto.setAnnotations(convertToAnnotations(resource.annotations()));
                        McpArgDto mcpArgDto = new McpArgDto();
                        mcpArgDto.setName("uri");
                        mcpArgDto.setDescription("uri");
                        mcpArgDto.setBindValue(resource.uri());
                        mcpArgDto.setKey(resource.uri());
                        mcpArgDto.setDataType(McpDataTypeEnum.String);
                        mcpArgDto.setRequire(true);
                        mcpResourceDto.setInputArgs(List.of(mcpArgDto));
                        mcpConfigDto.getResources().add(mcpResourceDto);
                    }
                }
            } catch (Exception e) {
                log.warn("listResources error {}", e.getMessage());
                // ignore 服务未提供resource功能时会报错
            }
            try {
                McpSchema.ListPromptsResult listPromptsResult = mcpAsyncClientWrapper.getClient().listPrompts().timeout(Duration.ofSeconds(30)).block();
                if (listPromptsResult != null) {
                    mcpConfigDto.setPrompts(new ArrayList<>());
                    for (McpSchema.Prompt prompt : listPromptsResult.prompts()) {
                        McpPromptDto mcpPromptDto = new McpPromptDto();
                        mcpPromptDto.setName(prompt.name());
                        mcpPromptDto.setDescription(prompt.description());
                        if (prompt.arguments() != null) {
                            mcpPromptDto.setInputArgs(prompt.arguments().stream().map(argument -> {
                                McpArgDto mcpArgDto = new McpArgDto();
                                mcpArgDto.setName(argument.name());
                                mcpArgDto.setKey(argument.name());
                                mcpArgDto.setDescription(argument.description());
                                mcpArgDto.setRequire(argument.required());
                                mcpArgDto.setDataType(McpDataTypeEnum.String);
                                return mcpArgDto;
                            }).collect(Collectors.toList()));
                        }
                        mcpConfigDto.getPrompts().add(mcpPromptDto);
                    }
                }
            } catch (Exception e) {
                log.warn("listPrompts error {}", e.getMessage());
                //  ignore 服务未提供resource功能时会报错
            }
        } catch (Throwable e) {
            log.error("McpDeployRpcServiceImpl.deploy error", e);
            throw e;
        } finally {
            if (mcpAsyncClientWrapper != null) {
                try {
                    mcpDeployRpcService.closeMcpClient(String.valueOf(id), MD5.MD5Encode(serverConfig), mcpAsyncClientWrapper);
                } catch (Exception e) {
                    //  ignore
                    log.error("McpDeployRpcServiceImpl.deploy closeMcpClient error", e);
                }
            }
        }
        McpConfig mcpConfig = new McpConfig();
        mcpConfig.setId(id);
        mcpConfig.setDeployStatus(DeployStatusEnum.Deployed);
        mcpConfig.setDeployed(new Date());
        mcpConfig.setConfig(JSON.toJSONString(mcpConfigDto));
        mcpConfig.setDeployedConfig(JSON.toJSONString(mcpConfigDto));
        if (modified != null) {
            mcpConfig.setModified(modified);
        }
        mcpConfigDomainService.update(mcpConfig);
    }


    private McpResourceContent.Annotations convertToAnnotations(McpSchema.Annotations annotations) {
        List<McpSchema.Role> audience = annotations.audience();
        List<String> audience0 = null;
        if (audience != null) {
            audience0 = audience.stream().map(McpSchema.Role::name).collect(Collectors.toList());
        }
        return new McpResourceContent.Annotations(audience0, annotations.priority());
    }

    private List<McpArgDto> convertInputSchemaToInputArgs(Map<String, Object> properties, List<String> requiredArgs) {
        List<McpArgDto> mcpArgDtos = new ArrayList<>();
        if (properties == null || properties.size() == 0) {
            return mcpArgDtos;
        }
        List<String> required = new ArrayList<>();
        if (requiredArgs != null) {
            required.addAll(requiredArgs);
        }
        return properties.entrySet().stream().map(entry -> {
            McpArgDto mcpArgDto = new McpArgDto();
            String name = entry.getKey();
            mcpArgDto.setName(name);
            mcpArgDto.setKey(name);
            if (required.contains(name)) {
                mcpArgDto.setRequire(true);
            }
            if (entry.getValue() instanceof Map<?, ?>) {
                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                mcpArgDto.setDescription(value.get("description") != null ? value.get("description").toString() : "");
                mcpArgDto.setBindValue(value.get("default") != null ? value.get("default").toString() : null);
                if ("object".equals(value.get("type"))) {
                    mcpArgDto.setDataType(McpDataTypeEnum.Object);
                    mcpArgDto.setSubArgs(convertInputSchemaToInputArgs((Map<String, Object>) value.get("properties"), (List<String>) value.get("required")));
                } else if ("array".equals(value.get("type"))) {
                    Map<String, Object> itemSchema = (Map<String, Object>) value.get("items");
                    if (itemSchema != null) {
                        if ("object".equals(itemSchema.get("type"))) {
                            mcpArgDto.setDataType(McpDataTypeEnum.Array_Object);
                            mcpArgDto.setSubArgs(convertInputSchemaToInputArgs((Map<String, Object>) itemSchema.get("properties"), (List<String>) itemSchema.get("required")));
                        } else if ("number".equals(itemSchema.get("type"))) {
                            mcpArgDto.setDataType(McpDataTypeEnum.Array_Number);
                        } else if ("integer".equals(itemSchema.get("type"))) {
                            mcpArgDto.setDataType(McpDataTypeEnum.Array_Integer);
                        } else if ("boolean".equals(itemSchema.get("type"))) {
                            mcpArgDto.setDataType(McpDataTypeEnum.Array_Boolean);
                        } else {
                            mcpArgDto.setDataType(McpDataTypeEnum.Array_String);
                        }
                    }
                } else if ("number".equals(value.get("type"))) {
                    mcpArgDto.setDataType(McpDataTypeEnum.Number);
                } else if ("integer".equals(value.get("type"))) {
                    mcpArgDto.setDataType(McpDataTypeEnum.Integer);
                } else if ("boolean".equals(value.get("type"))) {
                    mcpArgDto.setDataType(McpDataTypeEnum.Boolean);
                } else {
                    mcpArgDto.setDataType(McpDataTypeEnum.String);
                }
                if (value.get("enum") != null) {
                    List<?> enums = (List<?>) value.get("enum");
                    mcpArgDto.setEnums(enums.stream().map(Object::toString).collect(Collectors.toList()));
                }
            }
            return mcpArgDto;
        }).toList();
    }
}
