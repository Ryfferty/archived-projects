package com.xspaceagi.system.domain.service;

import com.xspaceagi.system.spec.enums.TenantStatus;
import com.xspaceagi.system.infra.dao.entity.Tenant;
import com.xspaceagi.system.infra.dao.entity.User;

import java.util.List;

public interface UserDomainService {

    void add(User user);

    void update(User user);

    void delete(Long userId);

    User queryById(Long userId);

    User queryByEmail(String email);

    User queryByPhone(String phone);

    User queryByPhoneAndPassword(String phone, String password);

    User queryByUserName(String userName);

    User queryUserByUid(String uid);

    List<User> queryUserListByIds(List<Long> userIds);

    /**
     * 根据用户UID列表查询用户列表,uid是字符串
     * 
     * @param uids 用户UID列表,字符串
     * @return 用户列表
     */
    List<User> queryUserListByUids(List<String> uids);

    List<Long> queryUserIdList(Long lastId, Integer size);
    
   
    /**
     * 获取指定状态的租户列表
     * 
     * @param status 租户状态
     * @return 指定状态的租户列表
     */
    List<Tenant> queryTenantsByStatus(TenantStatus status);
}
