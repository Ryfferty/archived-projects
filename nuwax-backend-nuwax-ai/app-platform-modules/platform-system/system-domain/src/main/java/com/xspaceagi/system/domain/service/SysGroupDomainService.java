package com.xspaceagi.system.domain.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.system.domain.model.GroupBindMenuModel;
import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.infra.dao.entity.SysDataPermission;
import com.xspaceagi.system.infra.dao.entity.SysGroup;
import com.xspaceagi.system.infra.dao.entity.User;
import com.xspaceagi.system.spec.common.UserContext;

/**
 * 用户组领域服务
 */
public interface SysGroupDomainService {


    /**
     * 添加用户组
     */
    void addGroup(SysGroup group, UserContext userContext);

    /**
     * 更新用户组
     * @return 是否更新了status
     */
    boolean updateGroup(SysGroup group, UserContext userContext);

    /**
     * 用户组绑定数据权限（全量覆盖）
     */
    void bindDataPermission(Long groupId, SysDataPermission dataPermission, UserContext userContext);

    /**
     * 删除角色
     */
    void deleteGroup(Long groupId, UserContext userContext);

    /**
     * 根据ID查询用户组
     */
    SysGroup queryGroupById(Long groupId);

    /**
     * 根据ID批量查询用户组
     */
    List<SysGroup> queryGroupListByIds(List<Long> groupIds);

    /**
     * 根据编码查询角色
     */
    SysGroup queryGroupByCode(String groupCode);

    /**
     * 查询角色列表
     */
    List<SysGroup> queryGroupList(SysGroup group);

    /**
     * 根据用户组ID查询用户列表
     */
    List<User> getUserListByGroupId(Long groupId);

    /**
     * 根据用户组ID分页查询用户列表，支持按userName模糊筛选
     */
    IPage<User> getUserPageByGroupId(Long groupId, String userName, long pageNo, long pageSize);

    /**
     * 根据用户组ID查询用户数量（轻量查询，仅 COUNT）
     */
    long countUsersByGroupId(Long groupId);

    /**
     * 根据用户组ID查询用户ID列表（轻量查询，仅查关联表，不加载 User 实体）
     */
    List<Long> getUserIdsByGroupId(Long groupId);

    /**
     * 根据用户ID查询角色列表
     */
    List<SysGroup> queryGroupListByUserId(Long userId);

    /**
     * 用户组绑定用户（全量覆盖）
     */
    void groupBindUser(Long groupId, List<Long> userIds, UserContext userContext);

    /**
     * 用户组添加用户（增量，单条插入）
     */
    void groupAddUser(Long groupId, Long userId, UserContext userContext);

    /**
     * 用户组移除用户（增量，单条删除）
     */
    void groupRemoveUser(Long groupId, Long userId, UserContext userContext);

    /**
     * 用户绑定用户组（全量覆盖）
     */
    void userBindGroup(Long userId, List<Long> groupIds, UserContext userContext);

    /**
     * 绑定菜单权限（全量覆盖）
     */
    void bindMenu(GroupBindMenuModel model, UserContext userContext);

    /**
     * 查询用户组已绑定的菜单及资源权限（树形结构）
     */
    List<MenuNode> getMenuTreeByGroupId(Long groupId);

    /**
     * 批量调整用户组顺序
     */
    void batchUpdateGroupSort(List<SortIndex> sortIndexList, UserContext userContext);

}
