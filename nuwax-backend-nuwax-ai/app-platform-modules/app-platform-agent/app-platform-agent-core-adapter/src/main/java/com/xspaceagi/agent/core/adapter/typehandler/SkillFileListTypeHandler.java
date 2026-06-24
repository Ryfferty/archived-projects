package com.xspaceagi.agent.core.adapter.typehandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xspaceagi.agent.core.adapter.dto.SkillFileDto;
import com.xspaceagi.system.spec.common.ListJsonTypeHandler;

import java.util.List;

public class SkillFileListTypeHandler extends ListJsonTypeHandler<SkillFileDto> {

    @Override
    protected TypeReference<List<SkillFileDto>> getTypeReference() {
        return new TypeReference<List<SkillFileDto>>() {
        };
    }
}