package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysUserRole;
import com.xspaceagi.system.infra.dao.mapper.SysUserRoleMapper;
import com.xspaceagi.system.infra.dao.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联Service实现
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
}

