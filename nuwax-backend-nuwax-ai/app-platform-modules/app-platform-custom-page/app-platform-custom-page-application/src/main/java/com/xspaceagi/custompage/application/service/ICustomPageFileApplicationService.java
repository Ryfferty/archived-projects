package com.xspaceagi.custompage.application.service;

import com.xspaceagi.system.spec.common.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * 文件服务
 */
public interface ICustomPageFileApplicationService {

    /**
     * 获取静态文件
     */
    ResponseEntity<StreamingResponseBody> getStaticFile(String requestPath, String staticPrefix, String targetPrefix, String logId, UserContext userContext);

}