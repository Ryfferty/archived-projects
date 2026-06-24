package com.xspaceagi.agent.core.adapter.application;

import com.xspaceagi.agent.core.adapter.dto.ExportTemplateDto;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.system.application.dto.UserDto;

public interface TemplateExportOrImportService {

    ExportTemplateDto queryTemplateConfig(Published.TargetType targetType, Long targetId);

    Long importTemplateConfig(UserDto user, Long spaceId, Published.TargetType targetType, String templateConfig);
}
