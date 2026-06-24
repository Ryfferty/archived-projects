package com.xspaceagi.knowledge.core.application.translator;

import com.xspaceagi.knowledge.domain.model.fulltext.RawSegmentFullTextModel;
import com.xspaceagi.knowledge.domain.model.KnowledgeRawSegmentModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识库全文检索数据转换器
 * 
 * @author system
 * @date 2025-03-31
 */
@Component
public class KnowledgeFullTextSearchTranslator {

    /**
     * 转换原始分段模型为全文检索模型
     * 
     * @param segment 原始分段模型
     * @param tenantId 租户ID
     * @param spaceId 空间ID
     * @return 全文检索模型
     */
    public RawSegmentFullTextModel toFullTextModel(KnowledgeRawSegmentModel segment, Long tenantId, Long spaceId) {
        if (segment == null) {
            return null;
        }

        RawSegmentFullTextModel model = RawSegmentFullTextModel.builder()
            .rawId(segment.getId())
            .kbId(segment.getKbId())
            .docId(segment.getDocId())
            .rawText(segment.getRawTxt())
            .sortIndex(segment.getSortIndex())
            .tenantId(tenantId)
            .spaceId(spaceId)
            .created(segment.getCreated())
            .build();

        return model;
    }

    /**
     * 批量转换原始分段模型为全文检索模型
     * 
     * @param segments 原始分段模型列表
     * @param tenantId 租户ID
     * @param spaceId 空间ID
     * @return 全文检索模型列表
     */
    public List<RawSegmentFullTextModel> toFullTextModelList(
            List<KnowledgeRawSegmentModel> segments, Long tenantId, Long spaceId) {
        if (segments == null) {
            return null;
        }

        return segments.stream()
            .map(segment -> toFullTextModel(segment, tenantId, spaceId))
            .collect(Collectors.toList());
    }
}
