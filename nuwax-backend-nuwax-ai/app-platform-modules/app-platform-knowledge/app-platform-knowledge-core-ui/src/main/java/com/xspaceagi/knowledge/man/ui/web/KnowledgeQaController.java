package com.xspaceagi.knowledge.man.ui.web;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xspaceagi.system.application.dto.SpaceDto;
import com.xspaceagi.system.application.service.SpaceApplicationService;
import com.xspaceagi.system.domain.log.LogPrint;
import com.xspaceagi.system.infra.service.QueryVoListDelegateService;
import com.xspaceagi.system.spec.annotation.RequireResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.knowledge.core.application.service.IKnowledgeQaSegmentApplicationService;
import com.xspaceagi.knowledge.domain.dto.EmbeddingStatusDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAQueryDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAResDto;
import com.xspaceagi.knowledge.domain.model.KnowledgeQaSegmentModel;
import com.xspaceagi.knowledge.domain.repository.IKnowledgeQaSegmentRepository;
import com.xspaceagi.knowledge.man.ui.web.base.BaseController;
import com.xspaceagi.knowledge.man.ui.web.dto.config.KnowledgeConfigDeleteRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.qa.KnowledgeQaSegmentAddRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.qa.KnowledgeQaSegmentQueryRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.qa.KnowledgeQaSegmentUpdateRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.qa.KnowledgeQaSegmentVo;
import com.xspaceagi.system.sdk.operate.ActionType;
import com.xspaceagi.system.sdk.operate.OperationLogReporter;
import com.xspaceagi.system.sdk.operate.SystemEnum;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.spec.page.PageQueryParamVo;
import com.xspaceagi.system.spec.page.PageQueryVo;
import com.xspaceagi.system.spec.page.SuperPage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import static com.xspaceagi.system.spec.enums.ResourceEnum.*;

@Tag(name = "知识库-问答配置接口")
@RestController
@RequestMapping("/api/knowledge/qa")
@Slf4j
public class KnowledgeQaController extends BaseController {

    @Resource
    private QueryVoListDelegateService queryVoListDelegateService;

    @Resource
    private IKnowledgeQaSegmentApplicationService knowledgeQaSegmentApplicationService;

    @Resource
    private IKnowledgeQaSegmentRepository knowledgeQaSegmentRepository;
    @Resource
    private SpaceApplicationService spaceApplicationService;

    @RequireResource(COMPONENT_LIB_QUERY_DETAIL)
    @OperationLogReporter(actionType = ActionType.QUERY, action = "数据列表查询", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-数据列表查询")
    @Operation(summary = "数据列表查询")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public ReqResult<IPage<KnowledgeQaSegmentVo>> list(
            @RequestBody PageQueryVo<KnowledgeQaSegmentQueryRequest> pageQueryVo) {
        var userContext = this.getUser();
        var userId = userContext.getUserId();
        var filter = pageQueryVo.getQueryFilter();
        pageQueryVo.setQueryFilter(filter);

        PageQueryParamVo pageQueryParamVo = new PageQueryParamVo(pageQueryVo);
        if (!isAdmin()) {
            // 查询用户有权限的空间,限制访问空间,比如工作流查询全部知识库,要限制用户有权限的空间下的知识库
            var spaceList = this.spaceApplicationService.queryListByUserId(userId);
            var spaceIds = spaceList.stream().map(SpaceDto::getId)
                    .toList();
            pageQueryParamVo.getQueryMap().put("authSpaceIds", spaceIds);
        }
        SuperPage<KnowledgeQaSegmentModel> superPage = this.queryVoListDelegateService.queryVoList(
                this.knowledgeQaSegmentRepository,
                pageQueryParamVo, null);

        var userModelList = superPage.getRecords();

        // 类型转换
        List<KnowledgeQaSegmentVo> userDtoList = userModelList.stream()
                .map(KnowledgeQaSegmentVo::convert2Dto)
                .toList();
        SuperPage<KnowledgeQaSegmentVo> iPage = SuperPage.build(superPage, userDtoList);
        iPage.setCurrent(pageQueryVo.getCurrent());
        iPage.setSize(pageQueryVo.getPageSize());

        return ReqResult.success(iPage);
    }

    @RequireResource(COMPONENT_LIB_MODIFY)
    @OperationLogReporter(actionType = ActionType.DELETE, action = "数据删除接口", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-数据删除接口")
    @Operation(summary = "数据删除接口")
    @RequestMapping(path = "/deleteById", method = RequestMethod.POST)
    public ReqResult<Void> delete(KnowledgeConfigDeleteRequest request)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        var id = request.getId();
        knowledgeQaSegmentApplicationService.deleteById(id);
        return ReqResult.success();

    }

    @RequireResource(COMPONENT_LIB_QUERY_DETAIL)
    @OperationLogReporter(actionType = ActionType.QUERY, action = "数据详情查询", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-数据详情查询")
    @Operation(summary = "数据详情查询")
    @RequestMapping(path = "/detailById", method = RequestMethod.GET)
    public ReqResult<KnowledgeQaSegmentVo> detailById(@Schema(description = "数据ID") Long dataId)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        var model = knowledgeQaSegmentApplicationService.queryOneInfoById(dataId);
        var knowledgeQaSegmentVo = KnowledgeQaSegmentVo.convert2Dto(model);
        return ReqResult.success(knowledgeQaSegmentVo);

    }

    @RequireResource(COMPONENT_LIB_MODIFY)
    @OperationLogReporter(actionType = ActionType.MODIFY, action = "数据更新接口", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-数据更新接口")
    @Operation(summary = "数据更新接口")
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ReqResult<Long> update(@RequestBody KnowledgeQaSegmentUpdateRequest updateDto)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        var userContext = this.getUser();
        var model = KnowledgeQaSegmentUpdateRequest.convert2Model(updateDto);
        var id = knowledgeQaSegmentApplicationService.updateInfo(model, userContext);
        return ReqResult.success(id);

    }

    @RequireResource(COMPONENT_LIB_MODIFY)
    @OperationLogReporter(actionType = ActionType.ADD, action = "数据新增接口", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-数据新增接口")
    @Operation(summary = "数据新增接口", description = "新增数据")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ReqResult<Long> add(@RequestBody KnowledgeQaSegmentAddRequest addDto)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        var userContext = this.getUser();
        var model = KnowledgeQaSegmentAddRequest.convert2Model(addDto);
        var id = knowledgeQaSegmentApplicationService.addInfo(model, userContext);
        return ReqResult.success(id);
    }

    @RequireResource(COMPONENT_LIB_QUERY_DETAIL)
    @OperationLogReporter(actionType = ActionType.QUERY, action = "查看Doc嵌入状态", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-查看Doc嵌入状态")
    @Operation(summary = "查看Doc嵌入状态")
    @RequestMapping(path = "/doc/embeddingStatus/{docId}", method = RequestMethod.GET)
    public ReqResult<EmbeddingStatusDto> queryEmbeddingStatus(@PathVariable Long docId) {

        var dataStatus = knowledgeQaSegmentApplicationService.queryEmbeddingStatus(docId);

        return ReqResult.success(dataStatus);
    }

    @RequireResource(COMPONENT_LIB_QUERY_DETAIL)
    @OperationLogReporter(actionType = ActionType.QUERY, action = "搜索单个知识库（它不关心知识库本身的状态，用于后台调试）", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-搜索单个知识库（它不关心知识库本身的状态，用于后台调试）")
    @Operation(summary = "搜索单个知识库（它不关心知识库本身的状态，用于后台调试）")
    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public ReqResult<List<QAResDto>> search(@RequestBody @Valid QAQueryDto qaQueryDto) {

        var qaResDtoList = knowledgeQaSegmentApplicationService.searchForWeb(qaQueryDto, true);

        return ReqResult.success(qaResDtoList);
    }

    @RequireResource(COMPONENT_LIB_IMPORT)
    @OperationLogReporter(actionType = ActionType.ADD, action = "批量导入Excel问答数据", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-批量导入Excel问答数据")
    @Operation(summary = "批量导入Excel问答数据", description = "通过Excel文件批量导入问答数据")
    @RequestMapping(path = "/importExcel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ReqResult<Void> importExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("kbId") Long kbId) {

        var userContext = this.getUser();

        knowledgeQaSegmentApplicationService.importQaFromExcel(kbId, file, userContext);

        return ReqResult.success();
    }

    @OperationLogReporter(actionType = ActionType.QUERY, action = "下载Excel问答模板", objectName = "知识库问答", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库问答]-下载Excel问答模板")
    @Operation(summary = "下载Excel问答模板", description = "下载Excel问答导入模板")
    @RequestMapping(path = "/downloadExcelTemplate", method = RequestMethod.GET)
    public void downloadExcelTemplate(HttpServletResponse response) throws IOException {
        byte[] excelContent = knowledgeQaSegmentApplicationService.getExcelTemplate();

        String filename = "知识库问答导入模板.xlsx";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename);
        response.setContentLength(excelContent.length);

        response.getOutputStream().write(excelContent);
        response.getOutputStream().flush();
    }
}
