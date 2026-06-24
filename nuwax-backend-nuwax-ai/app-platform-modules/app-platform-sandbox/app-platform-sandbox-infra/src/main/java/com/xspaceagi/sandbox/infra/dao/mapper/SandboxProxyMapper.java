package com.xspaceagi.sandbox.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxProxy;
import org.apache.ibatis.annotations.Mapper;

/**
 * 临时代理 Mapper 接口
 */
@Mapper
public interface SandboxProxyMapper extends BaseMapper<SandboxProxy> {

}