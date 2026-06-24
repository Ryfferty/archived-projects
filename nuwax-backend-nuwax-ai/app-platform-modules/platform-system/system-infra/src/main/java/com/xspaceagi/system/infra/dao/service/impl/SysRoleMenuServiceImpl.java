package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysRoleMenu;
import com.xspaceagi.system.infra.dao.mapper.SysRoleMenuMapper;
import com.xspaceagi.system.infra.dao.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色资源关联Service实现
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
}

