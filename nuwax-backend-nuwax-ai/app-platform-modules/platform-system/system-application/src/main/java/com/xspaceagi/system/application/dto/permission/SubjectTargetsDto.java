package com.xspaceagi.system.application.dto.permission;

import com.xspaceagi.system.infra.dao.entity.SysGroup;
import com.xspaceagi.system.infra.dao.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 主体访问权限的目标对象（角色/用户组）
 */
@Data
public class SubjectTargetsDto implements Serializable {

    @Schema(description = "可访问的角色列表")
    private List<SysRole> roles = new ArrayList<>();

    @Schema(description = "可访问的用户组列表")
    private List<SysGroup> groups = new ArrayList<>();
}
