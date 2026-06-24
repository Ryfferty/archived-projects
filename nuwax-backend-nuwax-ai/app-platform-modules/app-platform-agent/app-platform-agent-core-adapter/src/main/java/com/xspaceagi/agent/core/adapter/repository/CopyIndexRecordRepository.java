package com.xspaceagi.agent.core.adapter.repository;

public interface CopyIndexRecordRepository {

    int increment(Object type, Object key);

    String newCopyName(Object type, Object key, String originalName);
}
