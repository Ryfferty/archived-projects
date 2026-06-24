package com.xspaceagi.system.domain.service;


import com.xspaceagi.system.infra.dao.entity.Space;
import com.xspaceagi.system.infra.dao.entity.SpaceUser;

import java.util.List;

public interface SpaceDomainService {

    /**
     * 添加
     *
     * @param space
     */
    void add(Space space);

    /**
     * 向空间添加用户
     */
    void addSpaceUser(SpaceUser spaceUser);

    /**
     * 删除空间用户
     *
     * @param spaceId
     * @param userId
     */
    void deleteSpaceUser(Long spaceId, Long userId);

    /**
     * 删除空间
     *
     * @param spaceId
     */
    void delete(Long spaceId);

    /**
     * 更新空间
     *
     * @param space
     */
    void update(Space space);

    /**
     * 更新空间用户
     */
    void updateSpaceUser(SpaceUser spaceUser);

    /**
     * 根据ID查询空间
     *
     * @param spaceId
     * @return
     */
    Space queryById(Long spaceId);

    /**
     * 根据ID集合查询空间
     *
     * @param spaceIds
     * @return
     */
    List<Space> queryListByIds(List<Long> spaceIds);

    /**
     * 查询空间用户列表
     */
    List<SpaceUser> querySpaceUserList(Long spaceId);

    /**
     * 根据用户ID查询空间用户列表
     *
     * @param userId
     * @return
     */
    List<SpaceUser> querySpaceUserListByUserId(Long userId);

    /**
     * 查询空间用户
     *
     * @param spaceId
     * @param userId
     * @return
     */
    SpaceUser querySpaceUser(Long spaceId, Long userId);

    /**
     * 转移空间
     *
     * @param spaceId
     * @param targetUserId
     */
    void transfer(Long spaceId, Long targetUserId);

    /**
     * 统计工作空间总数
     */
    Long countTotalSpaces();

    Long countUserCreatedTeamSpaces(Long userId);
}
