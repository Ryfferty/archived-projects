package com.xspaceagi.system.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.system.infra.dao.entity.SysDataPermission;
import com.xspaceagi.system.infra.dao.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据权限Mapper
 */
@Mapper
public interface SysDataPermissionMapper extends BaseMapper<SysDataPermission> {
}
