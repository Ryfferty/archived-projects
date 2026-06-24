package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.SysGroupMenu;
import com.xspaceagi.system.infra.dao.mapper.SysGroupMenuMapper;
import com.xspaceagi.system.infra.dao.service.SysGroupMenuService;
import org.springframework.stereotype.Service;

/**
 * 用户组与菜单关联Service实现
 */
@Service
public class SysGroupMenuServiceImpl extends ServiceImpl<SysGroupMenuMapper, SysGroupMenu> implements SysGroupMenuService {
}


