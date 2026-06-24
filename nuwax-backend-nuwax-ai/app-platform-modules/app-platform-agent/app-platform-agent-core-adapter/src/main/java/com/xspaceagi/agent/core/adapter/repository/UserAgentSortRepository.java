package com.xspaceagi.agent.core.adapter.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.agent.core.adapter.repository.entity.UserAgentSort;

import java.util.List;
import java.util.Map;

public interface UserAgentSortRepository extends IService<UserAgentSort> {

    void updateSort(Long userId, List<String> categories, Map<String, List<Long>> agentSortConfig);
}
