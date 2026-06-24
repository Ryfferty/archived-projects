package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysRole;
import com.xspaceagi.system.infra.dao.mapper.SysRoleMapper;
import com.xspaceagi.system.infra.dao.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 系统角色Service实现
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}

