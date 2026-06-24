package com.xspaceagi.knowledge.assembler;

import org.springframework.stereotype.Service;

import com.xspaceagi.knowledge.domain.model.KnowledgeConfigModel;
import com.xspaceagi.knowledge.sdk.request.KnowledgeCreateRequestVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KnowledgeModelApiAssembler {

    public static KnowledgeConfigModel convertFromVo(KnowledgeCreateRequestVo vo) {
        KnowledgeConfigModel knowledgeConfigModel = new KnowledgeConfigModel();
        knowledgeConfigModel.setId(null);
        knowledgeConfigModel.setName(vo.getName());
        knowledgeConfigModel.setDescription(vo.getDescription());
        knowledgeConfigModel.setPubStatus(null);
        knowledgeConfigModel.setDataType(vo.getDataType());
        knowledgeConfigModel.setEmbeddingModelId(null);
        knowledgeConfigModel.setChatModelId(null);
        knowledgeConfigModel.setSpaceId(vo.getSpaceId());
        knowledgeConfigModel.setIcon(vo.getIcon());
        knowledgeConfigModel.setFileSize(null);
        knowledgeConfigModel.setCreated(null);
        knowledgeConfigModel.setCreatorId(vo.getUserId());
        knowledgeConfigModel.setCreatorName(null);
        knowledgeConfigModel.setModified(null);
        knowledgeConfigModel.setModifiedId(null);
        knowledgeConfigModel.setModifiedName(null);
        return knowledgeConfigModel;

    }

}
