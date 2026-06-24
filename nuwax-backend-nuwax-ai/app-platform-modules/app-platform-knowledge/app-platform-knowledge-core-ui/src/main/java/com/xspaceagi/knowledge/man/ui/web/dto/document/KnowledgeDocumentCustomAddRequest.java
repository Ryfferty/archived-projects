package com.xspaceagi.knowledge.man.ui.web.dto.document;

import com.xspaceagi.knowledge.core.spec.enums.KnowledgeDataTypeEnum;
import com.xspaceagi.knowledge.sdk.enums.KnowledgePubStatusEnum;
import com.xspaceagi.knowledge.sdk.vo.SegmentConfigModel;
import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Schema(description = "知识库文档-手动自定义新增")
@Getter
@Setter
public class KnowledgeDocumentCustomAddRequest implements Serializable {


    @Schema(description = "知识库ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long kbId;


    @Schema(description = "文档名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "文件内容")
    private String fileContent;

//    @Schema(description = "文件类型,默认值:1, 1:URL访问文件;2:自定义文本内容")
//    private Integer dataType;
//


    @Schema(description = "快速自动分段与清洗,true:无需分段设置,自动使用默认值")
    private Boolean autoSegmentConfigFlag;


    @Schema(description = "分段设置")
    private SegmentConfigModel segmentConfig;

    /**
     * 手动添加文档内容
     */
    public static KnowledgeDocumentModel convert2ModelForCustom(KnowledgeDocumentCustomAddRequest addDto) {

        KnowledgeDocumentModel knowledgeDocumentModel = new KnowledgeDocumentModel();
        knowledgeDocumentModel.setId(null);
        knowledgeDocumentModel.setKbId(addDto.getKbId());
        knowledgeDocumentModel.setSpaceId(null);
        knowledgeDocumentModel.setName(addDto.getName());
        //dataType = 2,自定义文本,才生效
        knowledgeDocumentModel.setFileContent(addDto.getFileContent());
        //docUrl 给个空字符串,满足数据库非空要求
        knowledgeDocumentModel.setDocUrl("");
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


        //默认文件 url地址的方式记录存储
        knowledgeDocumentModel.setDataType(KnowledgeDataTypeEnum.CUSTOM_TEXT.getCode());

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


        return knowledgeDocumentModel;

    }

}
