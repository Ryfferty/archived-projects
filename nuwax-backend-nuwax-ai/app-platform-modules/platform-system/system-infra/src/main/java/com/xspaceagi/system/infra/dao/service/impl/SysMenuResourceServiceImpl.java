package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysMenuResource;
import com.xspaceagi.system.infra.dao.mapper.SysMenuResourceMapper;
import com.xspaceagi.system.infra.dao.service.SysMenuResourceService;
import org.springframework.stereotype.Service;

/**
 * 菜单资源关联Service实现
 */
@Service
public class SysMenuResourceServiceImpl extends ServiceImpl<SysMenuResourceMapper, SysMenuResource> implements SysMenuResourceService {
}


