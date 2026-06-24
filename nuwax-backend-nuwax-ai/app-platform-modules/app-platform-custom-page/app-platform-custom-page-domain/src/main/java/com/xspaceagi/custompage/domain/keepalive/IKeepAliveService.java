package com.xspaceagi.custompage.domain.keepalive;

import java.util.Date;
import java.util.Map;

import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;

public interface IKeepAliveService {

    /**
     * 处理保活请求
     */
    ReqResult<Map<String, Object>> handleKeepAlive(Long projectId, UserContext userContext);

    /**
     * 更新保活信息
     */
    void updateKeepAlive(Long projectId,
            Date keepAliveTime,
            Integer devRunning,
            Integer devPid,
            Integer devPort,
            UserContext userContext);

    /**
     * 删除保活缓存
     */
    void removeKeepAliveCache(Long projectId);
}