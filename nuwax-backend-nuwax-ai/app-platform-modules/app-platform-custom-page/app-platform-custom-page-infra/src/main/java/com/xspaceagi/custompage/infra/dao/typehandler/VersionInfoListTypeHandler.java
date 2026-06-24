package com.xspaceagi.custompage.infra.dao.typehandler;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xspaceagi.custompage.sdk.dto.VersionInfoDto;
import com.xspaceagi.system.spec.common.ListJsonTypeHandler;

/**
 * 版本列表 TypeHandler
 */
public class VersionInfoListTypeHandler extends ListJsonTypeHandler<VersionInfoDto> {

    @Override
    protected TypeReference<List<VersionInfoDto>> getTypeReference() {
        return new TypeReference<List<VersionInfoDto>>() {
        };
    }
}