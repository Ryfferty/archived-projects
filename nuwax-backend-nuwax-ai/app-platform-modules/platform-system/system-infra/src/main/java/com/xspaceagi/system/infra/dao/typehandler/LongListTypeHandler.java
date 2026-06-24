package com.xspaceagi.system.infra.dao.typehandler;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xspaceagi.system.spec.common.ListJsonTypeHandler;

/**
 * Long列表 TypeHandler
 */
public class LongListTypeHandler extends ListJsonTypeHandler<Long> {

    @Override
    protected TypeReference<List<Long>> getTypeReference() {
        return new TypeReference<List<Long>>() {
        };
    }
}

