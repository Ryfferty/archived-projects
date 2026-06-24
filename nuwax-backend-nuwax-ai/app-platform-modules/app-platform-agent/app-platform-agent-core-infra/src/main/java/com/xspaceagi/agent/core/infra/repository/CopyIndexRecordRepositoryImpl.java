package com.xspaceagi.agent.core.infra.repository;

import com.xspaceagi.agent.core.adapter.repository.CopyIndexRecordRepository;
import com.xspaceagi.system.spec.utils.MD5;
import com.xspaceagi.system.spec.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CopyIndexRecordRepositoryImpl implements CopyIndexRecordRepository {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public int increment(Object type, Object key) {
        Long index = redisUtil.increment("copy_index:" + type + ":" + key, 1);
        if (index == null) {
            return 0;
        }
        return index.intValue();
    }

    @Override
    public String newCopyName(Object type, Object key, String originalName) {
        if (originalName == null) {
            return null;
        }
        String finalKey = key + ":" + MD5.MD5Encode(originalName);
        return originalName + "_" + increment(type, finalKey);
    }
}
