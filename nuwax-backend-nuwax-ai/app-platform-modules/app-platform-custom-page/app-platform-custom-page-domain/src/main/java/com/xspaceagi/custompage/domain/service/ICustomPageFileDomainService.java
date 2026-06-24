package com.xspaceagi.custompage.domain.service;

import com.xspaceagi.system.spec.common.UserContext;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

/**
 * 文件领域服务
 */
public interface ICustomPageFileDomainService {

    /**
     * 获取静态文件（流式返回）
     */
    Flux<DataBuffer> getStaticFile(String targetPrefix, String relativePath, String logId, UserContext userContext);

}

