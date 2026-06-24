package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysGroup;
import com.xspaceagi.system.infra.dao.mapper.SysGroupMapper;
import com.xspaceagi.system.infra.dao.service.SysGroupService;
import org.springframework.stereotype.Service;

/**
 * 用户组Service实现
 */
@Service
public class SysGroupServiceImpl extends ServiceImpl<SysGroupMapper, SysGroup> implements SysGroupService {
}


