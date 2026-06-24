package com.xspaceagi.knowledge.core.infra.translator.impl;

import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeTask;
import com.xspaceagi.knowledge.core.infra.translator.IKnowledgeTaskTranslator;
import com.xspaceagi.knowledge.domain.model.KnowledgeTaskModel;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class KnowledgeTaskTranslatorImpl implements IKnowledgeTaskTranslator {

    @Override
    public KnowledgeTaskModel convertToModel(KnowledgeTask entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        KnowledgeTaskModel knowledgeTaskModel = new KnowledgeTaskModel();
        knowledgeTaskModel.setId(entity.getId());
        knowledgeTaskModel.setKbId(entity.getKbId());
        knowledgeTaskModel.setSpaceId(entity.getSpaceId());
        knowledgeTaskModel.setDocId(entity.getDocId());
        knowledgeTaskModel.setType(entity.getType());
        knowledgeTaskModel.setTid(entity.getTid());
        knowledgeTaskModel.setName(entity.getName());
        knowledgeTaskModel.setStatus(entity.getStatus());
        knowledgeTaskModel.setMaxRetryCnt(entity.getMaxRetryCnt());
        knowledgeTaskModel.setRetryCnt(entity.getRetryCnt());
        knowledgeTaskModel.setResult(entity.getResult());
        knowledgeTaskModel.setTenantId(entity.getTenantId());
        knowledgeTaskModel.setCreated(entity.getCreated());
        knowledgeTaskModel.setCreatorId(entity.getCreatorId());
        knowledgeTaskModel.setCreatorName(entity.getCreatorName());
        knowledgeTaskModel.setModified(entity.getModified());
        return knowledgeTaskModel;
    }

    @Override
    public KnowledgeTask convertToEntity(KnowledgeTaskModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        KnowledgeTask knowledgeTask = new KnowledgeTask();
        knowledgeTask.setId(model.getId());
        knowledgeTask.setKbId(model.getKbId());
        knowledgeTask.setSpaceId(model.getSpaceId());
        knowledgeTask.setDocId(model.getDocId());
        knowledgeTask.setType(model.getType());
        knowledgeTask.setTid(model.getTid());
        knowledgeTask.setName(model.getName());
        knowledgeTask.setStatus(model.getStatus());
        knowledgeTask.setMaxRetryCnt(model.getMaxRetryCnt());
        knowledgeTask.setRetryCnt(model.getRetryCnt());
        knowledgeTask.setResult(model.getResult());
        knowledgeTask.setTenantId(model.getTenantId());
        knowledgeTask.setCreated(model.getCreated());
        knowledgeTask.setCreatorId(model.getCreatorId());
        knowledgeTask.setCreatorName(model.getCreatorName());
        knowledgeTask.setModified(model.getModified());
        return knowledgeTask;
    }
}
