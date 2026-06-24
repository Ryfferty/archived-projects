package com.xspaceagi.knowledge.core.application.service.old;

import org.springframework.stereotype.Service;

@Service
public class DocumentApplicationServiceImpl {
//
//    @Resource
//    private DocumentDomainService documentDomainService;
//
//    @Resource
//    private DocParserService docParserService;
//
//
//    @Override
//    public IPage<QASegmentDto> getQASegmentList(PageQueryVo<QASegmentQueryDto> pageQueryVo) {
//
//        IPage<QASegment> qaSegments =
//                documentDomainService.getQASegmentList(pageQueryVo);
//
//        List<QASegmentDto> qaSegmentDtos = qaSegments.getRecords().stream().map(qaSegment -> {
//            QASegmentDto qaSegmentDto = new QASegmentDto();
//            BeanUtils.copyProperties(qaSegment, qaSegmentDto);
//            return qaSegmentDto;
//        }).collect(Collectors.toList());
//
//        IPage<QASegmentDto> qaSegmentDtoIPage =
//                new Page<>(qaSegments.getCurrent(), qaSegments.getSize(),
//                        qaSegments.getTotal());
//        qaSegmentDtoIPage.setRecords(qaSegmentDtos);
//        return qaSegmentDtoIPage;
//    }
//
//    @Override
//    public IPage<RawSegmentDto> getRawSegmentList(PageQueryVo<RawSegmentDto> pageQueryVo) {
//        IPage<RawSegment> rawSegments =
//                documentDomainService.getRawSegmentList(pageQueryVo);
//
//        List<RawSegmentDto> rawSegmentDtos = documentDomainService.getRawSegmentList(pageQueryVo).getRecords().stream().map(rawSegment -> {
//            RawSegmentDto rawSegmentDto = new RawSegmentDto();
//            BeanUtils.copyProperties(rawSegment, rawSegmentDto);
//            return rawSegmentDto;
//        }).collect(Collectors.toList());
//
//        IPage<RawSegmentDto> rawSegmentDtoIPage =
//                new Page<>(rawSegments.getCurrent(), rawSegments.getSize(),
//                        rawSegments.getTotal());
//        rawSegmentDtoIPage.setRecords(rawSegmentDtos);
//        return rawSegmentDtoIPage;
//    }
//
//    private void segmentDoc(DocumentDto documentDto) {
//        try {
//            docParserService.parse(documentDto);
//        } catch (Exception e) {
//            throw new RuntimeException("解析文档失败" + e);
//        }
//    }
//
//    @Override
//    @DSTransactional
//    public Long addDocument(DocumentDto documentDto) {
//        Document document = new Document();
//        BeanUtils.copyProperties(documentDto, document);
//        document.setHasQa(Boolean.FALSE);
//        document.setHasEmbedding(Boolean.FALSE);
//        // 分段方式
//        document.setSegment(JSON.toJSONString(documentDto.getSegmentConfig()));
//        Long docID = documentDomainService.addDocument(document);
//        documentDto.setDocId(docID);
//        // 分段
//        segmentDoc(documentDto);
//        return docID;
//    }
//
//    @Override
//    public void delDocument(Long docID) {
//        documentDomainService.delDocument(docID);
//    }
//
//
//    @Override
//    public void updateDocument(DocumentUpdateDto documentUpdateDto) {
//        Document document = new Document();
//        BeanUtils.copyProperties(documentUpdateDto, document);
//        if (documentUpdateDto.getSegmentConfig() != null) {
//            document.setSegment(JSON.toJSONString(documentUpdateDto.getSegmentConfig()));
//        }
//        documentDomainService.updateDocument(document);
//    }
//
//    @Override
//    public DocumentDto getOne(Long docID) {
//        Document document = documentDomainService.getOne(docID);
//        if (document != null) {
//            DocumentDto documentDto = new DocumentDto();
//            BeanUtils.copyProperties(document, documentDto);
//            documentDto.setSegmentConfig(JSON.parseObject(document.getSegment(), SegmentConfigModel.class));
//            return documentDto;
//        }
//        return null;
//    }
//
//    @Override
//    public void generateQAs(Long docID) {
//        documentDomainService.generateQAs(docID);
//    }
//
//    @Override
//    public void generateEmbeddings(Long docID) {
//        documentDomainService.generateEmbeddings(docID);
//    }
//
//    @Override
//    public EmbeddingStatusDto queryEmbeddingStatus(Long docID) {
//        return documentDomainService.queryEmbeddingStatus(docID);
//    }
//
//    @Override
//    public void addQA(QASegmentDto segmentDto) {
//        QASegment segment = new QASegment();
//        BeanUtils.copyProperties(segmentDto, segment);
//
//        Document document = documentDomainService.getOne(segmentDto.getDocId());
//        segment.setKbId(document.getKbId());
//
//        documentDomainService.addQA(segment);
//    }
//
//    @Override
//    public void addQAWithEmbedding(QASegmentDto segmentDto) {
//        QASegment segment = new QASegment();
//        BeanUtils.copyProperties(segmentDto, segment);
//
//        Document document = documentDomainService.getOne(segmentDto.getDocId());
//        segment.setKbId(document.getKbId());
//
//        documentDomainService.addAndEmbedQA(segment);
//    }
//
//    @Override
//    public void delQA(Long qaID) {
//        documentDomainService.delQA(qaID);
//    }
//
//    @Override
//    public void updateQA(QAUpdateDto qaUpdateDto) {
//        documentDomainService.updateQA(qaUpdateDto);
//    }
}
