package com.xspaceagi.compose.domain.dto;

import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class FieldCreationResult {
    private CustomTableDefinitionModel updatedTableModel;
    private Map<String, String> newFieldMapping;
} 