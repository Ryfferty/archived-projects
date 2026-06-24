package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysUserGroup;
import com.xspaceagi.system.infra.dao.mapper.SysUserGroupMapper;
import com.xspaceagi.system.infra.dao.service.SysUserGroupService;
import org.springframework.stereotype.Service;

/**
 * 用户与用户组关联Service实现
 */
@Service
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements SysUserGroupService {
}


