package com.xspaceagi.knowledge.core.infra.translator.impl;

import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeRawSegment;
import com.xspaceagi.knowledge.core.infra.translator.IKnowledgeRawSegmentTranslator;
import com.xspaceagi.knowledge.domain.model.KnowledgeRawSegmentModel;
import com.xspaceagi.system.spec.enums.YnEnum;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeRawSegmentTranslatorImpl implements IKnowledgeRawSegmentTranslator {
    @Override
    public KnowledgeRawSegmentModel convertToModel(KnowledgeRawSegment entity) {
        if (entity == null) {
            return null;
        }
        KnowledgeRawSegmentModel knowledgeRawSegmentModel = new KnowledgeRawSegmentModel();
        knowledgeRawSegmentModel.setId(entity.getId());
        knowledgeRawSegmentModel.setDocId(entity.getDocId());
        knowledgeRawSegmentModel.setRawTxt(entity.getRawTxt());
        knowledgeRawSegmentModel.setKbId(entity.getKbId());
        knowledgeRawSegmentModel.setSortIndex(entity.getSortIndex());
        knowledgeRawSegmentModel.setSpaceId(entity.getSpaceId());
        knowledgeRawSegmentModel.setQaStatus(entity.getQaStatus());
        knowledgeRawSegmentModel.setCreated(entity.getCreated());
        knowledgeRawSegmentModel.setCreatorId(entity.getCreatorId());
        knowledgeRawSegmentModel.setCreatorName(entity.getCreatorName());
        knowledgeRawSegmentModel.setModified(entity.getModified());
        knowledgeRawSegmentModel.setModifiedId(entity.getModifiedId());
        knowledgeRawSegmentModel.setModifiedName(entity.getModifiedName());
        knowledgeRawSegmentModel.setTenantId(entity.getTenantId());
        knowledgeRawSegmentModel.setFulltextSyncStatus(entity.getFulltextSyncStatus());
        knowledgeRawSegmentModel.setFulltextSyncTime(entity.getFulltextSyncTime());
        return knowledgeRawSegmentModel;

    }

    @Override
    public KnowledgeRawSegment convertToEntity(KnowledgeRawSegmentModel model) {
        if (model == null) {
            return null;
        }
        KnowledgeRawSegment knowledgeRawSegment = new KnowledgeRawSegment();
        knowledgeRawSegment.setId(model.getId());
        knowledgeRawSegment.setDocId(model.getDocId());
        knowledgeRawSegment.setRawTxt(model.getRawTxt());
        knowledgeRawSegment.setKbId(model.getKbId());
        knowledgeRawSegment.setSortIndex(model.getSortIndex());
        knowledgeRawSegment.setSpaceId(model.getSpaceId());
        knowledgeRawSegment.setQaStatus(model.getQaStatus());
        knowledgeRawSegment.setCreated(model.getCreated());
        knowledgeRawSegment.setCreatorId(model.getCreatorId());
        knowledgeRawSegment.setCreatorName(model.getCreatorName());
        knowledgeRawSegment.setModified(model.getModified());
        knowledgeRawSegment.setModifiedId(model.getModifiedId());
        knowledgeRawSegment.setModifiedName(model.getModifiedName());
        knowledgeRawSegment.setYn(YnEnum.Y.getKey());
        knowledgeRawSegment.setTenantId(model.getTenantId());
        knowledgeRawSegment.setFulltextSyncStatus(model.getFulltextSyncStatus());
        knowledgeRawSegment.setFulltextSyncTime(model.getFulltextSyncTime());
        return knowledgeRawSegment;

    }
}
