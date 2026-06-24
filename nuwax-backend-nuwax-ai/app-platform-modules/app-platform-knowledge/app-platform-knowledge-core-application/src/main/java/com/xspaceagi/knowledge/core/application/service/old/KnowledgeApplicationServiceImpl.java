package com.xspaceagi.knowledge.core.application.service.old;

import org.springframework.stereotype.Service;

@Service
public class KnowledgeApplicationServiceImpl {
//
//    @Resource
//    private KnowledgeDomainService knowledgeDomainService;
//
//    @Override
//    public Long addKnowledgeBase(KnowledgeBaseDto knowledgeBaseDto) {
//        KnowledgeBase knowledgeBase = new KnowledgeBase();
//        BeanUtils.copyProperties(knowledgeBaseDto, knowledgeBase);
//        knowledgeDomainService.addKnowledgeBase(knowledgeBase);
//
//        return knowledgeBase.getKbId();
//    }
//
//    @Override
//    public void updateKnowledgeBase(KnowledgeBaseUpdateDto knowledgeBaseUpdateDto) {
//        KnowledgeBase knowledgeBase = new KnowledgeBase();
//        BeanUtils.copyProperties(knowledgeBaseUpdateDto, knowledgeBase);
//        knowledgeDomainService.updateKnowledgeBase(knowledgeBase);
//    }
//
//    @Override
//    public List<KnowledgeBaseDto> getAppKnowledgeBaseDtoList(Long appId) {
//        List<KnowledgeBaseDto> knowledgeBaseDtoList = new ArrayList<>();
//
//        List<KnowledgeBase> knowledgeBaseList = knowledgeDomainService.getAppKnowledgeBaseList(appId);
//        knowledgeBaseList.forEach(knowledgeBase -> knowledgeBaseDtoList.add(convertKnowledgeBaseToDto(knowledgeBase)));
//        return knowledgeBaseDtoList;
//    }
//
//    private KnowledgeBaseDto convertKnowledgeBaseToDto(KnowledgeBase knowledgeBase) {
//        KnowledgeBaseDto knowledgeBaseDto = new KnowledgeBaseDto();
//        BeanUtils.copyProperties(knowledgeBase, knowledgeBaseDto);
//        return knowledgeBaseDto;
//    }
//
//    @Override
//    public List<DocumentDto> getDocumentDtoList(Long knowledgeBaseId) {
//        List<DocumentDto> documentDtoList = new ArrayList<>();
//        knowledgeDomainService.getDocumentList(knowledgeBaseId).forEach(document -> documentDtoList.add(convertDocumentToDto(document)));
//        return documentDtoList;
//    }
//
//    private DocumentDto convertDocumentToDto(Document document) {
//        DocumentDto documentDto = new DocumentDto();
//        BeanUtils.copyProperties(document, documentDto);
//        documentDto.setSegmentConfig(JSON.parseObject(document.getSegment(), SegmentConfigModel.class));
//        return documentDto;
//    }
//
//    @Override
//    public KnowledgeBaseDto getOne(Long knowledgeBaseId) {
//        KnowledgeBaseDto knowledgeBaseDto = new KnowledgeBaseDto();
//        KnowledgeBase knowledgeBase = knowledgeDomainService.getOne(knowledgeBaseId);
//        BeanUtils.copyProperties(knowledgeBase, knowledgeBaseDto);
//        return knowledgeBaseDto;
//    }
//
//    @Override
//    public List<QAResDto> search(QAQueryDto qaQueryDto) {
//        return knowledgeDomainService.search(qaQueryDto, true);
//    }
//
//    @Override
//    public List<QAResDto> search(List<QAQueryDto> qaQueryDtoList) {
//        return knowledgeDomainService.search(qaQueryDtoList);
//    }
//
//    @Override
//    public List<QAResDto> searchInApp(QAQueryAppDto qaQueryAppDto) {
//        return knowledgeDomainService.searchInApp(qaQueryAppDto);
//    }
//
//    @Override
//    public void removeKnowledgeBase(Long knowledgeBaseId) {
//        knowledgeDomainService.removeKnowledgeBase(knowledgeBaseId);
//    }
}
