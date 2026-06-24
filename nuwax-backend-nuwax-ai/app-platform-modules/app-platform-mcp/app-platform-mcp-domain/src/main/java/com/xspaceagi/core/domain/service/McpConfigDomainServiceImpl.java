package com.xspaceagi.core.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.mcp.adapter.domain.McpConfigDomainService;
import com.xspaceagi.mcp.adapter.dto.McpPageQueryDto;
import com.xspaceagi.mcp.adapter.repository.McpConfigRepository;
import com.xspaceagi.mcp.adapter.repository.entity.McpConfig;
import com.xspaceagi.mcp.sdk.enums.DeployStatusEnum;
import com.xspaceagi.mcp.sdk.enums.InstallTypeEnum;
import com.xspaceagi.system.spec.tenant.thread.TenantFunctions;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class McpConfigDomainServiceImpl implements McpConfigDomainService {

    @Resource
    private McpConfigRepository mcpConfigRepository;

    @Override
    public void add(McpConfig mcpConfig) {
        mcpConfigRepository.save(mcpConfig);
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id must be non-null");
        mcpConfigRepository.removeById(id);
    }

    @Override
    public void update(McpConfig mcpConfig) {
        Assert.notNull(mcpConfig.getId(), "id must be non-null");
        mcpConfigRepository.updateById(mcpConfig);
    }

    @Override
    public McpConfig getById(Long id) {
        Assert.notNull(id, "id must be non-null");
        return mcpConfigRepository.getById(id);
    }

    @Override
    public McpConfig getBySpaceIdAndUid(Long spaceId, String uid) {
        return mcpConfigRepository.getOne(new QueryWrapper<McpConfig>().eq("space_id", spaceId).eq("uid", uid));
    }

    @Override
    public List<McpConfig> queryListBySpaceId(Long spaceId) {
        LambdaQueryWrapper<McpConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(McpConfig::getSpaceId, spaceId);
        //任务智能体代理mcp类型，不用展示出来
        queryWrapper.and(publishedLambdaQueryWrapper -> publishedLambdaQueryWrapper.ne(McpConfig::getCategory, "Proxy").or().isNull(McpConfig::getCategory));
        queryWrapper.orderByDesc(McpConfig::getModified);
        return mcpConfigRepository.list(queryWrapper);
    }

    @Override
    public Page<McpConfig> queryDeployedMcpList(McpPageQueryDto mcpPageQueryDto) {
        LambdaQueryWrapper<McpConfig> queryWrapper = new LambdaQueryWrapper<>();
        if (mcpPageQueryDto.getSpaceId() == null) {
            queryWrapper.eq(McpConfig::getSpaceId, -1L);
        } else if (mcpPageQueryDto.getJustReturnSpaceData() != null && mcpPageQueryDto.getJustReturnSpaceData()) {
            queryWrapper.eq(McpConfig::getSpaceId, mcpPageQueryDto.getSpaceId());
        } else {
            queryWrapper.in(McpConfig::getSpaceId, List.of(mcpPageQueryDto.getSpaceId(), -1L));
        }
        if (StringUtils.isNotBlank(mcpPageQueryDto.getKw())) {
            queryWrapper.like(McpConfig::getName, mcpPageQueryDto.getKw());
        }
        if (mcpPageQueryDto.getCreatorIds() != null && !mcpPageQueryDto.getCreatorIds().isEmpty()) {
            queryWrapper.in(McpConfig::getCreatorId, mcpPageQueryDto.getCreatorIds());
        }
        queryWrapper.and(publishedLambdaQueryWrapper -> publishedLambdaQueryWrapper.ne(McpConfig::getCategory, "Proxy").or().isNull(McpConfig::getCategory));
        queryWrapper.notIn(McpConfig::getDeployStatus, DeployStatusEnum.Initialization, DeployStatusEnum.Stopped);
        queryWrapper.isNotNull(McpConfig::getDeployedConfig);
        queryWrapper.orderByDesc(McpConfig::getModified);
        return mcpConfigRepository.page(new Page<>(mcpPageQueryDto.getPage(), mcpPageQueryDto.getPageSize()), queryWrapper);
    }

    @Override
    public Page<McpConfig> queryDeployedMcpListForManage(McpPageQueryDto mcpPageQueryDto) {
        LambdaQueryWrapper<McpConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(mcpPageQueryDto.getSpaceId() != null && mcpPageQueryDto.getSpaceId() > 0, McpConfig::getSpaceId, mcpPageQueryDto.getSpaceId());
        queryWrapper.and(publishedLambdaQueryWrapper -> publishedLambdaQueryWrapper.ne(McpConfig::getCategory, "Proxy").or().isNull(McpConfig::getCategory));
        queryWrapper.in(mcpPageQueryDto.getCreatorIds() != null && !mcpPageQueryDto.getCreatorIds().isEmpty(), McpConfig::getCreatorId, mcpPageQueryDto.getCreatorIds());
        queryWrapper.notIn(McpConfig::getDeployStatus, DeployStatusEnum.Initialization, DeployStatusEnum.Stopped);
        queryWrapper.like(mcpPageQueryDto.getKw() != null, McpConfig::getName, mcpPageQueryDto.getKw());
        queryWrapper.isNotNull(McpConfig::getDeployedConfig);
        queryWrapper.orderByDesc(McpConfig::getModified);
        return mcpConfigRepository.page(new Page<>(mcpPageQueryDto.getPage(), mcpPageQueryDto.getPageSize()), queryWrapper);
    }

    @Override
    public IPage<McpConfig> queryPage(McpConfig entityFilter, int pageNum, int pageSize) {
        LambdaQueryWrapper<McpConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntity(entityFilter);
        return mcpConfigRepository.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public List<McpConfig> queryDeployedSSEMcpConfigList(Long lastId, int size) {
        LambdaQueryWrapper<McpConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(McpConfig::getId, lastId);
        queryWrapper.eq(McpConfig::getDeployStatus, DeployStatusEnum.Deployed);
        queryWrapper.eq(McpConfig::getInstallType, InstallTypeEnum.SSE);
        queryWrapper.last("limit " + size);
        queryWrapper.orderByAsc(McpConfig::getId);
        return TenantFunctions.callWithIgnoreCheck(() -> mcpConfigRepository.list(queryWrapper));
    }

    @Override
    public Long countTotalMcps() {
        return mcpConfigRepository.count();
    }
}
