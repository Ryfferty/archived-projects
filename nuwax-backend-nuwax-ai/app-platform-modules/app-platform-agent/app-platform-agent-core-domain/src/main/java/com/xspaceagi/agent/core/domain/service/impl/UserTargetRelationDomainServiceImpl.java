package com.xspaceagi.agent.core.domain.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xspaceagi.agent.core.adapter.repository.UserTargetRelationRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.PublishedStatistics;
import com.xspaceagi.agent.core.adapter.repository.entity.UserTargetRelation;
import com.xspaceagi.agent.core.domain.service.PublishDomainService;
import com.xspaceagi.agent.core.domain.service.UserTargetRelationDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserTargetRelationDomainServiceImpl implements UserTargetRelationDomainService {

    @Resource
    private UserTargetRelationRepository userTargetRelationRepository;

    @Resource
    private PublishDomainService publishDomainService;

    @Override
    @DSTransactional
    public boolean record(Long userId, Published.TargetType targetType, UserTargetRelation.OpType type, Long targetId) {
        Assert.notNull(userId, "userId不能为空");
        Assert.notNull(targetType, "targetType不能为空");
        Assert.notNull(targetId, "targetId不能为空");
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userId);
        queryWrapper.eq(UserTargetRelation::getTargetType, targetType);
        queryWrapper.eq(UserTargetRelation::getTargetId, targetId);
        queryWrapper.eq(UserTargetRelation::getType, type);
        if (userTargetRelationRepository.exists(queryWrapper)) {
            return false;
        }
        UserTargetRelation targetRelation = new UserTargetRelation();
        targetRelation.setUserId(userId);
        targetRelation.setTargetType(targetType);
        targetRelation.setTargetId(targetId);
        targetRelation.setType(type);
        userTargetRelationRepository.save(targetRelation);
        return true;
    }

    @Override
    @DSTransactional
    public boolean unRecord(Long userId, Published.TargetType targetType, UserTargetRelation.OpType type, Long targetId) {
        if (userTargetRelationRepository.exists(new LambdaQueryWrapper<UserTargetRelation>()
                .eq(UserTargetRelation::getUserId, userId)
                .eq(UserTargetRelation::getTargetType, targetType)
                .eq(UserTargetRelation::getTargetId, targetId)
                .eq(UserTargetRelation::getType, type))) {
            userTargetRelationRepository.remove(new LambdaQueryWrapper<UserTargetRelation>()
                    .eq(UserTargetRelation::getUserId, userId)
                    .eq(UserTargetRelation::getTargetType, targetType)
                    .eq(UserTargetRelation::getTargetId, targetId)
                    .eq(UserTargetRelation::getType, type));
            return true;
        }
        return false;
    }

    @Override
    public List<UserTargetRelation> queryRecentUseList(Long userId, Published.TargetType targetType, Integer size, Integer pageIndex) {
        Assert.notNull(userId, "userId must be non-null");
        Assert.notNull(size, "size must be non-null");
        if (size > 1000 || size <= 0) {
            size = 10;
        }
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userId);
        queryWrapper.eq(UserTargetRelation::getType, UserTargetRelation.OpType.Conversation);
        queryWrapper.eq(UserTargetRelation::getTargetType, targetType);
        queryWrapper.orderByDesc(UserTargetRelation::getModified);
        if (pageIndex != null && pageIndex > 0) {
            queryWrapper.last("limit " + (pageIndex - 1) * size + "," + size);
        } else {
            queryWrapper.last("limit " + size);
        }
        return userTargetRelationRepository.list(queryWrapper);
    }

    @Override
    public UserTargetRelation queryRecentUse(Long userId, Published.TargetType targetType, Long targetId) {
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userId);
        queryWrapper.eq(UserTargetRelation::getType, UserTargetRelation.OpType.Conversation);
        queryWrapper.eq(UserTargetRelation::getTargetType, targetType);
        queryWrapper.eq(UserTargetRelation::getTargetId, targetId);
        return userTargetRelationRepository.getOne(queryWrapper, false);
    }

    @Override
    public List<UserTargetRelation> queryCollectionList(Long userId, Published.TargetType targetType, Integer page, Integer size) {
        return queryList(userId, targetType, UserTargetRelation.OpType.Collect, page, size);
    }

    @Override
    public List<UserTargetRelation> queryDevCollectionList(Long userId, Published.TargetType targetType, Integer page, Integer size) {
        return queryList(userId, targetType, UserTargetRelation.OpType.DevCollect, page, size);
    }

    private List<UserTargetRelation> queryList(Long userId, Published.TargetType targetType, UserTargetRelation.OpType opType, Integer page, Integer size) {
        Assert.notNull(userId, "userId must be non-null");
        Assert.notNull(page, "page must be non-null");
        Assert.notNull(size, "size must be non-null");
        if (size > 100 || size <= 0) {
            size = 10;
        }
        if (page <= 0) {
            page = 1;
        }
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userId);
        queryWrapper.eq(UserTargetRelation::getTargetType, targetType);
        queryWrapper.eq(UserTargetRelation::getType, opType);
        queryWrapper.orderByDesc(UserTargetRelation::getId);
        queryWrapper.last("limit " + (page - 1) * size + "," + size);
        return userTargetRelationRepository.list(queryWrapper);
    }

    @Override
    public List<UserTargetRelation> queryRecentEditList(Long userId, Published.TargetType targetType, Integer size) {
        Assert.notNull(size, "size must be non-null");
        if (size > 100 || size <= 0) {
            size = 10;
        }
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userId);
        queryWrapper.eq(UserTargetRelation::getType, UserTargetRelation.OpType.Edit);
        queryWrapper.eq(UserTargetRelation::getTargetType, targetType);
        queryWrapper.orderByDesc(UserTargetRelation::getModified);
        queryWrapper.last("limit " + size);
        return userTargetRelationRepository.list(queryWrapper);
    }

    @Override
    public List<UserTargetRelation> queryUserTargetRelationByTargetIds(Long userId, Published.TargetType targetType,
                                                                       UserTargetRelation.OpType type, List<Long> targetIds) {
        Assert.notNull(userId, "userId must be non-null");
        Assert.notNull(targetType, "targetType must be non-null");
        Assert.notNull(targetIds, "targetIds must be non-null");
        if (targetIds.isEmpty()) {
            return Collections.emptyList();
        }
        return userTargetRelationRepository.list(new LambdaQueryWrapper<UserTargetRelation>()
                .eq(UserTargetRelation::getUserId, userId)
                .eq(UserTargetRelation::getTargetType, targetType)
                .eq(UserTargetRelation::getType, type)
                .in(UserTargetRelation::getTargetId, targetIds));
    }

    @Override
    public void addOrUpdateRecentEdit(UserTargetRelation userTargetRelation) {
        Assert.notNull(userTargetRelation, "userTargetRelation must be non-null");
        Assert.notNull(userTargetRelation.getUserId(), "userId must be non-null");
        Assert.notNull(userTargetRelation.getTargetType(), "targetType must be non-null");
        Assert.notNull(userTargetRelation.getTargetId(), "targetId must be non-null");
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userTargetRelation.getUserId());
        queryWrapper.eq(UserTargetRelation::getType, UserTargetRelation.OpType.Edit);
        queryWrapper.eq(UserTargetRelation::getTargetType, userTargetRelation.getTargetType());
        queryWrapper.eq(UserTargetRelation::getTargetId, userTargetRelation.getTargetId());
        if (userTargetRelationRepository.exists(queryWrapper)) {
            userTargetRelation.setModified(new Date());
            userTargetRelationRepository.update(userTargetRelation, queryWrapper);
        } else {
            userTargetRelationRepository.save(userTargetRelation);
        }
    }

    @Override
    public void addOrUpdateRecentUsed(UserTargetRelation userTargetRelation) {
        Assert.notNull(userTargetRelation, "userTargetRelation must be non-null");
        Assert.notNull(userTargetRelation.getUserId(), "userId must be non-null");
        Assert.notNull(userTargetRelation.getTargetId(), "targetId must be non-null");
        Assert.notNull(userTargetRelation.getTargetType(), "targetType must be non-null");
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userTargetRelation.getUserId());
        queryWrapper.eq(UserTargetRelation::getTargetType, userTargetRelation.getTargetType());
        queryWrapper.eq(UserTargetRelation::getType, UserTargetRelation.OpType.Conversation);
        queryWrapper.eq(UserTargetRelation::getTargetId, userTargetRelation.getTargetId());
        if (userTargetRelationRepository.exists(queryWrapper)) {
            userTargetRelation.setModified(new Date());
            userTargetRelationRepository.update(userTargetRelation, queryWrapper);
        } else {
            userTargetRelationRepository.save(userTargetRelation);
        }
    }

    @Override
    public void like(Long userId, Published.TargetType targetType, Long targetId) {
        //添加点赞，添加前先判断是否已经添加，不是remove
        LambdaQueryWrapper<UserTargetRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTargetRelation::getUserId, userId);
        queryWrapper.eq(UserTargetRelation::getTargetType, targetType);
        queryWrapper.eq(UserTargetRelation::getTargetId, targetId);
        queryWrapper.eq(UserTargetRelation::getType, UserTargetRelation.OpType.Like);
        if (userTargetRelationRepository.exists(queryWrapper)) {
            return;
        }

        UserTargetRelation userAgent = new UserTargetRelation();
        userAgent.setUserId(userId);
        userAgent.setTargetType(targetType);
        userAgent.setTargetId(targetId);
        userAgent.setType(UserTargetRelation.OpType.Like);
        userTargetRelationRepository.save(userAgent);
        publishDomainService.incStatisticsCount(targetType, targetId, PublishedStatistics.Key.LIKE_COUNT.getKey(), 1L);
    }

    @Override
    public void unLike(Long userId, Published.TargetType targetType, Long targetId) {
        if (userTargetRelationRepository.exists(new LambdaQueryWrapper<UserTargetRelation>()
                .eq(UserTargetRelation::getUserId, userId)
                .eq(UserTargetRelation::getTargetType, targetType)
                .eq(UserTargetRelation::getTargetId, targetId)
                .eq(UserTargetRelation::getType, UserTargetRelation.OpType.Like))) {
            userTargetRelationRepository.remove(new LambdaQueryWrapper<UserTargetRelation>()
                    .eq(UserTargetRelation::getUserId, userId)
                    .eq(UserTargetRelation::getTargetType, targetType)
                    .eq(UserTargetRelation::getTargetId, targetId)
                    .eq(UserTargetRelation::getType, UserTargetRelation.OpType.Like));

            publishDomainService.incStatisticsCount(targetType, targetId, PublishedStatistics.Key.LIKE_COUNT.getKey(), -1L);
        }
    }

    @Override
    public void delete(Long id) {
        userTargetRelationRepository.removeById(id);
    }
}
