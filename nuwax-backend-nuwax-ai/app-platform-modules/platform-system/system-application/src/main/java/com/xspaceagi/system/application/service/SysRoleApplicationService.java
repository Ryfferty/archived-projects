package com.xspaceagi.system.application.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.domain.model.RoleBindMenuModel;
import com.xspaceagi.system.infra.dao.entity.SysDataPermission;
import com.xspaceagi.system.infra.dao.entity.SysRole;
import com.xspaceagi.system.infra.dao.entity.User;
import com.xspaceagi.system.spec.common.UserContext;

/**
 * 系统角色应用服务
 */
public interface SysRoleApplicationService {
    
    /**
     * 添加角色
     */
    void addRole(SysRole role, UserContext userContext);

    /**
     * 更新角色
     */
    void updateRole(SysRole role, UserContext userContext);

    /**
     * 角色绑定数据权限（全量覆盖）
     */
    void bindDataPermission(Long roleId, SysDataPermission dataPermission, UserContext userContext);
    
    /**
     * 删除角色
     */
    void deleteRole(Long roleId, UserContext userContext);
    
    /**
     * 根据ID查询角色
     */
    SysRole getRoleById(Long roleId);

    /**
     * 根据ID批量查询角色
     */
    List<SysRole> listRolesByIds(List<Long> roleIds);
    
    /**
     * 根据编码查询角色
     */
    SysRole getRoleByCode(String roleCode);
    
    /**
     * 查询角色列表
     */
    List<SysRole> getRoleList(SysRole role);

    /**
     * 根据角色ID查询用户列表
     */
    List<User> getUserListByRoleId(Long roleId);

    /**
     * 根据角色ID分页查询用户列表，支持按userName模糊筛选
     */
    IPage<User> getUserPageByRoleId(Long roleId, String userName, long pageNo, long pageSize);

    /**
     * 根据用户ID查询角色列表
     */
    List<SysRole> getRoleListByUserId(Long userId);
    
    /**
     * 角色绑定用户（全量覆盖）
     */
    void roleBindUser(Long roleId, List<Long> userIds, UserContext userContext);

    /**
     * 角色添加用户
     */
    void roleAddUser(Long roleId, Long userId, UserContext userContext);

    /**
     * 角色移除用户
     */
    void roleRemoveUser(Long roleId, Long userId, UserContext userContext);

    /**
     * 用户绑定角色（全量覆盖）
     */
    void userBindRole(Long userId, List<Long> roleIds, UserContext userContext);

    /**
     * 角色绑定菜单权限（全量覆盖）
     */
    void bindMenu(RoleBindMenuModel model, UserContext userContext);

    /**
     * 查询角色已绑定的菜单树（包含资源权限）
     */
    List<MenuNode> getMenuTreeByRoleId(Long roleId);

    /**
     * 批量调整角色顺序
     */
    void batchUpdateRoleSort(List<SortIndex> sortIndexList, UserContext userContext);

}

