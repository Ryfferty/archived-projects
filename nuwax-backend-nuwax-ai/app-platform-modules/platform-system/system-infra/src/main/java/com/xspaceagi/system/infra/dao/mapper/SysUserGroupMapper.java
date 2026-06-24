package com.xspaceagi.system.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.system.infra.dao.entity.SysUserGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户与用户组关联Mapper
 */
@Mapper
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {
}


