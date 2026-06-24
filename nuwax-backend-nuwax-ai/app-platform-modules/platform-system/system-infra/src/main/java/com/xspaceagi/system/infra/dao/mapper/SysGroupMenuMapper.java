package com.xspaceagi.system.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.system.infra.dao.entity.SysGroupMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户组与菜单关联Mapper
 */
@Mapper
public interface SysGroupMenuMapper extends BaseMapper<SysGroupMenu> {
}


