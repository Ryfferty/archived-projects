package com.xspaceagi.custompage.infra.dao.typehandler;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xspaceagi.custompage.sdk.dto.DataSourceDto;
import com.xspaceagi.system.spec.common.ListJsonTypeHandler;

/**
 * 数据源列表 TypeHandler
 */
public class DataSourceListTypeHandler extends ListJsonTypeHandler<DataSourceDto> {

    @Override
    protected TypeReference<List<DataSourceDto>> getTypeReference() {
        return new TypeReference<List<DataSourceDto>>() {
        };
    }
}
