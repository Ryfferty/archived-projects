package com.xspaceagi.agent.core.infra.rpc;

import com.xspaceagi.system.sdk.service.UserShareApiService;
import com.xspaceagi.system.sdk.service.dto.UserShareDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserShareRpcService {

    @Resource
    private UserShareApiService userShareApiService;


    public UserShareDto addOrUpdateUserShare(UserShareDto userShareDto) {
        return userShareApiService.addOrUpdateUserShare(userShareDto);
    }

    public UserShareDto getUserShare(String shareKey, boolean expiredNotReturn) {
        return userShareApiService.getUserShare(shareKey, expiredNotReturn);
    }

    public UserShareDto getUserShare(UserShareDto.UserShareType type, String targetId, boolean expiredNotReturn) {
        return userShareApiService.getUserShare(type, targetId, expiredNotReturn);
    }
}
