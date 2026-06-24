package com.xspaceagi.knowledge.man.ui.web.dto.document;

import com.xspaceagi.knowledge.core.spec.enums.KnowledgeDataTypeEnum;
import com.xspaceagi.knowledge.sdk.enums.KnowledgePubStatusEnum;
import com.xspaceagi.knowledge.sdk.vo.SegmentConfigModel;
import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Schema(description = "知识库文档-新增请求参数")
@Getter
@Setter
public class KnowledgeDocumentAddRequest implements Serializable {


    @Schema(description = "知识库ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long kbId;


    @Schema(description = "文档文件列表")
    private List<FileInfoVo> fileList;


    @Schema(description = "快速自动分段与清洗,true:无需分段设置,自动使用默认值")
    private Boolean autoSegmentConfigFlag;


    @Schema(description = "分段设置")
    private SegmentConfigModel segmentConfig;


    /**
     * 批量上传文件,使用文件的url
     */
    public static List<KnowledgeDocumentModel> convert2Model(KnowledgeDocumentAddRequest addDto) {

        var docUrlList = new ArrayList<FileInfoVo>();
        if (CollectionUtils.isNotEmpty(addDto.getFileList())) {
            docUrlList.addAll(addDto.getFileList());
        }

        List<KnowledgeDocumentModel> docModelList = new ArrayList<>();
        for (FileInfoVo fileInfo : docUrlList) {
            KnowledgeDocumentModel knowledgeDocumentModel = new KnowledgeDocumentModel();
            knowledgeDocumentModel.setId(null);
            knowledgeDocumentModel.setKbId(addDto.getKbId());
            knowledgeDocumentModel.setSpaceId(null);
            knowledgeDocumentModel.setName(fileInfo.getName());
            knowledgeDocumentModel.setDocUrl(fileInfo.getDocUrl());
            knowledgeDocumentModel.setFileSize(fileInfo.getFileSize());
            knowledgeDocumentModel.setPubStatus(KnowledgePubStatusEnum.Waiting);
            knowledgeDocumentModel.setHasQa(Boolean.FALSE);
            knowledgeDocumentModel.setHasEmbedding(Boolean.FALSE);
            knowledgeDocumentModel.setSegmentConfig(addDto.getSegmentConfig());
            knowledgeDocumentModel.setCreated(null);
            knowledgeDocumentModel.setCreatorId(null);
            knowledgeDocumentModel.setCreatorName(null);
            knowledgeDocumentModel.setModified(null);
            knowledgeDocumentModel.setModifiedId(null);
            knowledgeDocumentModel.setModifiedName(null);

            //默认 url地址的方式记录存储
            knowledgeDocumentModel.setDataType(KnowledgeDataTypeEnum.URL_FILE.getCode());

            //默认自动分段与清洗
            var autoSegmentConfigFlag = true;
            if (Objects.nonNull(addDto.getAutoSegmentConfigFlag())) {
                autoSegmentConfigFlag = addDto.getAutoSegmentConfigFlag();
            }

            if (autoSegmentConfigFlag) {
                //自动使用默认值
                SegmentConfigModel segmentConfig = SegmentConfigModel.obtainDefaultModel();

                knowledgeDocumentModel.setSegmentConfig(segmentConfig);
            }
            docModelList.add(knowledgeDocumentModel);
        }


        return docModelList;

    }

}
