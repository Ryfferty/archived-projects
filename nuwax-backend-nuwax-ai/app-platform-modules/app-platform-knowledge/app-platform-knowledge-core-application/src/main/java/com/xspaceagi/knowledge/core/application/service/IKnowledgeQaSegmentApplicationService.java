package com.xspaceagi.knowledge.core.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xspaceagi.knowledge.domain.dto.EmbeddingStatusDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAQueryDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAResDto;
import com.xspaceagi.knowledge.domain.model.KnowledgeQaSegmentModel;
import com.xspaceagi.system.spec.common.UserContext;

public interface IKnowledgeQaSegmentApplicationService {

    /**
     * 模板配置详情
     *
     * @param id 模板id
     */
    KnowledgeQaSegmentModel queryOneInfoById(Long id);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新
     *
     * @param model 模型
     * @return id
     */
    Long updateInfo(KnowledgeQaSegmentModel model, UserContext userContext);

    /**
     * 新增
     */
    Long addInfo(KnowledgeQaSegmentModel model, UserContext userContext);

    /**
     * 知识库问答查询
     * 
     * @param qaQueryDto     问答查询
     * @param ignoreKBStatus 是否忽略知识库状态
     * @return
     */
    List<QAResDto> search(QAQueryDto qaQueryDto, boolean ignoreKBStatus);


    /**
     * 知识库问答查询,给前端网页用的,会校验空间权限
     *
     * @param qaQueryDto     问答查询
     * @param ignoreKBStatus 是否忽略知识库状态
     * @return
     */
    List<QAResDto> searchForWeb(QAQueryDto qaQueryDto, boolean ignoreKBStatus);

    /**
     * 查询文档的嵌入状态
     * 
     * @param docId 文档id
     * @return
     */
    EmbeddingStatusDto queryEmbeddingStatus(Long docId);

    /**
     * 查询待向量化的问答列表,对问答做向量化,根据字段:qaStatus=-1,created在days天前,
     * 
     * @param days     天数
     * @param pageSize 每页大小
     * @param pageNum  页码
     * @return
     */
    List<KnowledgeQaSegmentModel> queryListForEmbeddingQaAndEmbeddings(Integer days, Integer pageSize, Integer pageNum);

    /**
     * 批量导入Excel文件中的问答数据
     *
     * @param kbId        知识库ID
     * @param file        Excel文件
     * @param userContext 用户上下文
     */
    void importQaFromExcel(Long kbId, MultipartFile file, UserContext userContext);

    /**
     * 获取Excel模板内容
     *
     * @return Excel模板内容的字节数组
     */
    byte[] getExcelTemplate();

}
