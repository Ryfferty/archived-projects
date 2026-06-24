package com.xspaceagi.modelproxy.infra.rpc;

import com.xspaceagi.log.sdk.service.ILogRpcService;
import com.xspaceagi.log.sdk.vo.LogDocument;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogSaveRpcService {
    @Resource
    private ILogRpcService logRpcService;

    public void saveLog(LogDocument logDocument) {
        logRpcService.bulkIndex(List.of(logDocument));
    }
}
