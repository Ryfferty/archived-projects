package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.UserAgentSortRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.UserAgentSort;
import com.xspaceagi.agent.core.infra.dao.mapper.UserAgentSortMapper;
import com.xspaceagi.system.spec.common.RequestContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserAgentSortRepositoryImpl extends ServiceImpl<UserAgentSortMapper, UserAgentSort> implements UserAgentSortRepository {

    @Override
    @DSTransactional
    public void updateSort(Long userId, List<String> categories, Map<String, List<Long>> agentSortConfig) {
        //排序配置
        LambdaQueryWrapper<UserAgentSort> sortQueryWrapper = new LambdaQueryWrapper<>();
        sortQueryWrapper.eq(UserAgentSort::getUserId, RequestContext.get().getUserId());
        sortQueryWrapper.orderByAsc(UserAgentSort::getSort);
        List<UserAgentSort> userAgentSortList = list(sortQueryWrapper);
        //userAgentSortList以category转map
        Map<String, UserAgentSort> userAgentSortMap = userAgentSortList.stream().collect(Collectors.toMap(UserAgentSort::getCategory, userAgentSort -> userAgentSort, (c1, c2) -> c1));
        List<UserAgentSort> userAgentSortList1 = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categories)) {
            userAgentSortList.forEach(userAgentSort -> userAgentSort.setSort(Integer.MAX_VALUE));
            for (int i = 0; i < categories.size(); i++) {
                UserAgentSort userAgentSort = userAgentSortMap.get(categories.get(i));
                if (userAgentSort == null) {
                    userAgentSort = new UserAgentSort();
                    userAgentSort.setUserId(userId);
                    userAgentSort.setCategory(categories.get(i));
                    userAgentSort.setSort(i);
                    save(userAgentSort);
                } else {
                    userAgentSort.setSort(i);
                    userAgentSortList.remove(userAgentSort);
                }
                userAgentSortList1.add(userAgentSort);
            }
        }
        userAgentSortList1.addAll(userAgentSortList);
        //userAgentSortList1以category为key转map
        Map<String, UserAgentSort> userAgentCategorySortMap = userAgentSortList1.stream().collect(Collectors.toMap(UserAgentSort::getCategory, userAgentSort -> userAgentSort, (c1, c2) -> c1));
        if (agentSortConfig != null) {
            agentSortConfig.keySet().forEach(category -> {
                UserAgentSort userAgentSort = userAgentCategorySortMap.get(category);
                if (userAgentSort == null) {
                    userAgentSort = new UserAgentSort();
                    userAgentSort.setUserId(userId);
                    userAgentSort.setCategory(category);
                    userAgentSort.setSort(Integer.MAX_VALUE);
                    save(userAgentSort);
                    userAgentSortList1.add(userAgentSort);
                }
            });
            userAgentSortList1.forEach(userAgentSort -> {
                List<Long> longs = agentSortConfig.get(userAgentSort.getCategory());
                if (longs != null) {
                    userAgentSort.setAgentSortConfig(longs);
                }
            });
        }
        updateBatchById(userAgentSortList1);
    }
}
