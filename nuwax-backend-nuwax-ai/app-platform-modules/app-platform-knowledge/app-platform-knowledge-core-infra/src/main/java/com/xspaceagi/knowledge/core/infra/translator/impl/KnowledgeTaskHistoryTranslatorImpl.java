package com.xspaceagi.knowledge.core.infra.translator.impl;

import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeTaskHistory;
import com.xspaceagi.knowledge.core.infra.translator.IKnowledgeTaskHistoryTranslator;
import com.xspaceagi.knowledge.domain.model.KnowledgeTaskHistoryModel;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class KnowledgeTaskHistoryTranslatorImpl
        implements IKnowledgeTaskHistoryTranslator {

    @Override
    public KnowledgeTaskHistoryModel convertToModel(
            KnowledgeTaskHistory entity
    ) {
        if (Objects.isNull(entity)) {
            return null;
        }
        KnowledgeTaskHistoryModel knowledgeTaskHistoryModel =
                new KnowledgeTaskHistoryModel();
        knowledgeTaskHistoryModel.setId(entity.getId());
        knowledgeTaskHistoryModel.setKbId(entity.getKbId());
        knowledgeTaskHistoryModel.setSpaceId(entity.getSpaceId());
        knowledgeTaskHistoryModel.setDocId(entity.getDocId());
        knowledgeTaskHistoryModel.setType(entity.getType());
        knowledgeTaskHistoryModel.setTid(entity.getTid());
        knowledgeTaskHistoryModel.setName(entity.getName());
        knowledgeTaskHistoryModel.setStatus(entity.getStatus());
        knowledgeTaskHistoryModel.setMaxRetryCnt(entity.getMaxRetryCnt());
        knowledgeTaskHistoryModel.setRetryCnt(entity.getRetryCnt());
        knowledgeTaskHistoryModel.setResult(entity.getResult());
        knowledgeTaskHistoryModel.setTenantId(entity.getTenantId());
        knowledgeTaskHistoryModel.setCreated(entity.getCreated());
        knowledgeTaskHistoryModel.setCreatorId(entity.getCreatorId());
        knowledgeTaskHistoryModel.setCreatorName(entity.getCreatorName());
        knowledgeTaskHistoryModel.setModified(entity.getModified());
        return knowledgeTaskHistoryModel;
    }

    @Override
    public KnowledgeTaskHistory convertToEntity(
            KnowledgeTaskHistoryModel model
    ) {
        if (Objects.isNull(model)) {
            return null;
        }
        KnowledgeTaskHistory knowledgeTaskHistory = new KnowledgeTaskHistory();
        knowledgeTaskHistory.setId(model.getId());
        knowledgeTaskHistory.setKbId(model.getKbId());
        knowledgeTaskHistory.setSpaceId(model.getSpaceId());
        knowledgeTaskHistory.setDocId(model.getDocId());
        knowledgeTaskHistory.setType(model.getType());
        knowledgeTaskHistory.setTid(model.getTid());
        knowledgeTaskHistory.setName(model.getName());
        knowledgeTaskHistory.setStatus(model.getStatus());
        knowledgeTaskHistory.setMaxRetryCnt(model.getMaxRetryCnt());
        knowledgeTaskHistory.setRetryCnt(model.getRetryCnt());
        knowledgeTaskHistory.setResult(model.getResult());
        knowledgeTaskHistory.setTenantId(model.getTenantId());
        knowledgeTaskHistory.setCreated(model.getCreated());
        knowledgeTaskHistory.setCreatorId(model.getCreatorId());
        knowledgeTaskHistory.setCreatorName(model.getCreatorName());
        knowledgeTaskHistory.setModified(model.getModified());
        return knowledgeTaskHistory;
    }
}
