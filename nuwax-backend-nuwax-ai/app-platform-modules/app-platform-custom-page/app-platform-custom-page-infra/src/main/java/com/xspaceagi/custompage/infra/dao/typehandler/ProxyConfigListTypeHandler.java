package com.xspaceagi.custompage.infra.dao.typehandler;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xspaceagi.custompage.sdk.dto.ProxyConfig;
import com.xspaceagi.system.spec.common.ListJsonTypeHandler;

public class ProxyConfigListTypeHandler extends ListJsonTypeHandler<ProxyConfig> {

    @Override
    protected TypeReference<List<ProxyConfig>> getTypeReference() {
        return new TypeReference<List<ProxyConfig>>() {
        };
    }
}