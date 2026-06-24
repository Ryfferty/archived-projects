package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysDataPermission;
import com.xspaceagi.system.infra.dao.entity.SysMenu;
import com.xspaceagi.system.infra.dao.mapper.SysDataPermissionMapper;
import com.xspaceagi.system.infra.dao.mapper.SysMenuMapper;
import com.xspaceagi.system.infra.dao.service.SysDataPermissionService;
import com.xspaceagi.system.infra.dao.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 数据权限Service实现
 */
@Service
public class SysDataPermissionServiceImpl extends ServiceImpl<SysDataPermissionMapper, SysDataPermission> implements SysDataPermissionService {
}
