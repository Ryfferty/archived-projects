package com.xspaceagi.knowledge.man.ui.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.knowledge.core.application.service.IKnowledgeRawSegmentApplicationService;
import com.xspaceagi.knowledge.domain.model.KnowledgeRawSegmentModel;
import com.xspaceagi.knowledge.domain.repository.IKnowledgeRawSegmentRepository;
import com.xspaceagi.knowledge.man.ui.web.base.BaseController;
import com.xspaceagi.knowledge.man.ui.web.dto.config.KnowledgeConfigDeleteRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.segment.KnowledgeRawSegmentAddRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.segment.KnowledgeRawSegmentQueryRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.segment.KnowledgeRawSegmentUpdateRequest;
import com.xspaceagi.knowledge.man.ui.web.dto.segment.KnowledgeRawSegmentVo;
import com.xspaceagi.system.application.dto.SpaceDto;
import com.xspaceagi.system.application.service.SpaceApplicationService;
import com.xspaceagi.system.domain.log.LogPrint;
import com.xspaceagi.system.infra.service.QueryVoListDelegateService;
import com.xspaceagi.system.sdk.operate.ActionType;
import com.xspaceagi.system.sdk.operate.OperationLogReporter;
import com.xspaceagi.system.sdk.operate.SystemEnum;
import com.xspaceagi.system.spec.annotation.RequireResource;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.spec.page.PageQueryParamVo;
import com.xspaceagi.system.spec.page.PageQueryVo;
import com.xspaceagi.system.spec.page.SuperPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.xspaceagi.system.spec.enums.ResourceEnum.COMPONENT_LIB_MODIFY;
import static com.xspaceagi.system.spec.enums.ResourceEnum.COMPONENT_LIB_QUERY_DETAIL;

@Tag(name = "知识库-分段配置接口")
@RestController
@RequestMapping("/api/knowledge/rawSegment")
@Slf4j
public class KnowledgeRawSegmentController extends BaseController {

    @Resource
    private QueryVoListDelegateService queryVoListDelegateService;

    @Resource
    private IKnowledgeRawSegmentApplicationService knowledgeRawSegmentApplicationService;

    @Resource
    private IKnowledgeRawSegmentRepository knowledgeRawSegmentRepository;
    @Resource
    private SpaceApplicationService spaceApplicationService;

    @RequireResource(COMPONENT_LIB_QUERY_DETAIL)
    @OperationLogReporter(actionType = ActionType.QUERY, action = "数据列表查询", objectName = "知识库分段", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库分段]-数据列表查询")
    @Operation(summary = "数据列表查询")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public ReqResult<IPage<KnowledgeRawSegmentVo>> list(
            @RequestBody PageQueryVo<KnowledgeRawSegmentQueryRequest> pageQueryVo) {
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

        SuperPage<KnowledgeRawSegmentModel> superPage = this.queryVoListDelegateService.queryVoList(
                this.knowledgeRawSegmentRepository,
                pageQueryParamVo, null);

        var userModelList = superPage.getRecords();

        // 类型转换
        List<KnowledgeRawSegmentVo> userDtoList = userModelList.stream()
                .map(KnowledgeRawSegmentVo::convert2Dto)
                .toList();
        SuperPage<KnowledgeRawSegmentVo> iPage = SuperPage.build(superPage, userDtoList);
        iPage.setCurrent(pageQueryVo.getCurrent());
        iPage.setSize(pageQueryVo.getPageSize());

        return ReqResult.success(iPage);
    }

    @RequireResource(COMPONENT_LIB_MODIFY)
    @OperationLogReporter(actionType = ActionType.DELETE, action = "数据删除接口", objectName = "知识库分段", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库分段]-数据删除接口")
    @Operation(summary = "数据删除接口")
    @RequestMapping(path = "/deleteById", method = RequestMethod.GET)
    public ReqResult<Void> delete(KnowledgeConfigDeleteRequest request)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        var id = request.getId();
        var userContext = this.getUser();
        knowledgeRawSegmentApplicationService.deleteById(id, userContext);
        return ReqResult.success();

    }

    @RequireResource(COMPONENT_LIB_MODIFY)
    @OperationLogReporter(actionType = ActionType.QUERY, action = "数据详情查询", objectName = "知识库分段", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库分段]-数据详情查询")
    @Operation(summary = "数据详情查询")
    @RequestMapping(path = "/detailById", method = RequestMethod.GET)
    public ReqResult<KnowledgeRawSegmentVo> detailById(@Schema(description = "数据ID") Long dataId)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        var model = knowledgeRawSegmentApplicationService.queryOneInfoById(dataId);
        var knowledgeRawSegmentVo = KnowledgeRawSegmentVo.convert2Dto(model);
        return ReqResult.success(knowledgeRawSegmentVo);

    }

    @RequireResource(COMPONENT_LIB_MODIFY)
    @OperationLogReporter(actionType = ActionType.MODIFY, action = "数据更新接口", objectName = "知识库分段", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库分段]-数据更新接口")
    @Operation(summary = "数据更新接口")
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ReqResult<Long> update(@RequestBody KnowledgeRawSegmentUpdateRequest updateDto)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        var userContext = this.getUser();
        var model = KnowledgeRawSegmentUpdateRequest.convert2Model(updateDto);
        var id = knowledgeRawSegmentApplicationService.updateInfo(model, userContext);
        return ReqResult.success(id);

    }

    @RequireResource(COMPONENT_LIB_MODIFY)
    @OperationLogReporter(actionType = ActionType.ADD, action = "数据新增接口", objectName = "知识库分段", systemCode = SystemEnum.KNOWLEDGE_CONFIG)
    @LogPrint(step = "知识库[知识库分段]-数据新增接口")
    @Operation(summary = "数据新增接口", description = "新增数据")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ReqResult<Long> add(@RequestBody KnowledgeRawSegmentAddRequest addDto)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        var userContext = this.getUser();
        var model = KnowledgeRawSegmentAddRequest.convert2Model(addDto);

        var id = knowledgeRawSegmentApplicationService.addInfo(model, userContext);
        return ReqResult.success(id);
    }

}
