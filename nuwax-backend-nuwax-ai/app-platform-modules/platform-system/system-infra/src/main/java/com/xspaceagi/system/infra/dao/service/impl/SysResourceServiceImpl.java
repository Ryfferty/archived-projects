package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysResource;
import com.xspaceagi.system.infra.dao.mapper.SysResourceMapper;
import com.xspaceagi.system.infra.dao.service.SysResourceService;
import org.springframework.stereotype.Service;

/**
 * 系统资源Service实现
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {
}

