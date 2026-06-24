package com.xspaceagi.custompage.infra.dao.typehandler;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xspaceagi.custompage.sdk.dto.PageArgConfig;
import com.xspaceagi.system.spec.common.ListJsonTypeHandler;

public class PageArgConfigListTypeHandler extends ListJsonTypeHandler<PageArgConfig> {

    @Override
    protected TypeReference<List<PageArgConfig>> getTypeReference() {
        return new TypeReference<List<PageArgConfig>>() {
        };
    }
}