package com.xspaceagi.knowledge.core.infra.translator.impl;

import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeQaSegment;
import com.xspaceagi.knowledge.core.infra.translator.IKnowledgeQaSegmentTranslator;
import com.xspaceagi.knowledge.domain.model.KnowledgeQaSegmentModel;
import com.xspaceagi.system.spec.enums.YnEnum;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeQaSegmentTranslatorImpl implements IKnowledgeQaSegmentTranslator {

    @Override
    public KnowledgeQaSegmentModel convertToModel(KnowledgeQaSegment entity) {
        if (entity == null) {
            return null;
        }
        KnowledgeQaSegmentModel knowledgeQaSegmentModel = new KnowledgeQaSegmentModel();
        knowledgeQaSegmentModel.setId(entity.getId());
        knowledgeQaSegmentModel.setDocId(entity.getDocId());
        knowledgeQaSegmentModel.setRawId(entity.getRawId());
        knowledgeQaSegmentModel.setQuestion(entity.getQuestion());
        knowledgeQaSegmentModel.setAnswer(entity.getAnswer());
        knowledgeQaSegmentModel.setKbId(entity.getKbId());
        knowledgeQaSegmentModel.setHasEmbedding(entity.getHasEmbedding());
        knowledgeQaSegmentModel.setSpaceId(entity.getSpaceId());
        knowledgeQaSegmentModel.setCreated(entity.getCreated());
        knowledgeQaSegmentModel.setCreatorId(entity.getCreatorId());
        knowledgeQaSegmentModel.setCreatorName(entity.getCreatorName());
        knowledgeQaSegmentModel.setModified(entity.getModified());
        knowledgeQaSegmentModel.setModifiedId(entity.getModifiedId());
        knowledgeQaSegmentModel.setModifiedName(entity.getModifiedName());
        knowledgeQaSegmentModel.setTenantId(entity.getTenantId());
        return knowledgeQaSegmentModel;

    }

    @Override
    public KnowledgeQaSegment convertToEntity(KnowledgeQaSegmentModel model) {
        if (model == null) {
            return null;
        }
        KnowledgeQaSegment knowledgeQaSegment = new KnowledgeQaSegment();
        knowledgeQaSegment.setId(model.getId());
        knowledgeQaSegment.setDocId(model.getDocId());
        knowledgeQaSegment.setRawId(model.getRawId());
        knowledgeQaSegment.setQuestion(model.getQuestion());
        knowledgeQaSegment.setAnswer(model.getAnswer());
        knowledgeQaSegment.setKbId(model.getKbId());
        knowledgeQaSegment.setHasEmbedding(model.getHasEmbedding());
        knowledgeQaSegment.setSpaceId(model.getSpaceId());
        knowledgeQaSegment.setCreated(model.getCreated());
        knowledgeQaSegment.setCreatorId(model.getCreatorId());
        knowledgeQaSegment.setCreatorName(model.getCreatorName());
        knowledgeQaSegment.setModified(model.getModified());
        knowledgeQaSegment.setModifiedId(model.getModifiedId());
        knowledgeQaSegment.setModifiedName(model.getModifiedName());
        knowledgeQaSegment.setYn(YnEnum.Y.getKey());
        knowledgeQaSegment.setTenantId(model.getTenantId());
        return knowledgeQaSegment;

    }
}
