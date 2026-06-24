package com.xspaceagi.agent.core.domain.service;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.adapter.repository.entity.UserTargetRelation;

import java.util.List;

public interface UserTargetRelationDomainService {

    boolean record(Long userId, Published.TargetType targetType, UserTargetRelation.OpType opType, Long targetId);

    boolean unRecord(Long userId, Published.TargetType targetType, UserTargetRelation.OpType opType, Long targetId);

    List<UserTargetRelation> queryRecentUseList(Long userId, Published.TargetType targetType, Integer size, Integer pageIndex);

    UserTargetRelation queryRecentUse(Long userId, Published.TargetType targetType, Long targetId);

    List<UserTargetRelation> queryCollectionList(Long userId, Published.TargetType targetType, Integer page, Integer size);

    List<UserTargetRelation> queryDevCollectionList(Long userId, Published.TargetType targetType, Integer page, Integer size);

    List<UserTargetRelation> queryRecentEditList(Long userId, Published.TargetType targetType, Integer size);

    List<UserTargetRelation> queryUserTargetRelationByTargetIds(Long userId, Published.TargetType targetType, UserTargetRelation.OpType type, List<Long> targetIds);

    void addOrUpdateRecentEdit(UserTargetRelation userTargetRelation);

    void addOrUpdateRecentUsed(UserTargetRelation userTargetRelation);

    void like(Long userId, Published.TargetType targetType, Long targetId);

    void unLike(Long userId, Published.TargetType targetType, Long targetId);

    void delete(Long id);
}
