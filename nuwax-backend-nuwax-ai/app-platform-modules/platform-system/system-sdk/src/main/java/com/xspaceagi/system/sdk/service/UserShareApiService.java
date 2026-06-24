package com.xspaceagi.system.sdk.service;

import com.xspaceagi.system.sdk.service.dto.UserShareDto;

public interface UserShareApiService {

    UserShareDto addOrUpdateUserShare(UserShareDto userShareDto);

    UserShareDto getUserShare(String shareKey, boolean expiredNotReturn);

    UserShareDto getUserShare(UserShareDto.UserShareType type, String targetId, boolean expiredNotReturn);
}
