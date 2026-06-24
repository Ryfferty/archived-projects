package com.xspaceagi.sandbox.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 沙盒配置 Mapper 接口
 */
@Mapper
public interface SandboxConfigMapper extends BaseMapper<SandboxConfig> {

}
