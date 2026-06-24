package com.xspaceagi.agent.web.ui.dto;

import com.xspaceagi.agent.core.adapter.dto.config.InputArg;
import com.xspaceagi.agent.core.adapter.dto.config.OutputArgDto;
import com.xspaceagi.agent.core.spec.enums.AgentTypeEnum;
import com.xspaceagi.agent.core.spec.enums.InputTypeEnum;
import com.xspaceagi.agent.core.spec.enums.OutputTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AgentInfoDto implements Serializable {


     @Schema(description =  "agent ID")
    private Long agentId;

     @Schema(description =  "后续接口访问路径")
    private String path;

     @Schema(description =  "展示名称")
    private String pubName;

     @Schema(description =  "图标地址")
    private String iconUrl;

     @Schema(description =  "描述")
    private String description;

     @Schema(description =  "agent类型，Plugin 查询模式, Agent 会话模式")
    private AgentTypeEnum agentType;

     @Schema(description =  "输入类型 Text文本, Dict字典")
    private InputTypeEnum inputType;

     @Schema(description =  "输入参数，inputType为Dict和Array时有效")
    private List<InputArg> inputArgs;

     @Schema(description =  "输出数据类型，Text文本，Dict字典，Array数组")
    private OutputTypeEnum outputType;

     @Schema(description =  "输出参数列表，不定义则按照上文最后一个节点内容进行输出")
    private List<OutputArgDto> outputArgs;

     @Schema(description =  "允许对话框上传文件，agentType为Agent时有效")
    private Boolean allowAttachment;

     @Schema(description =  "开启对话框语音交互，agentType为Agent时有效")
    private Boolean openVoice;

     @Schema(description =  "是否流逝输出，agentType为Agent时有效")
    private Boolean streamOut;

     @Schema(description =  "首次打开聊天框自动回复消息，agentType为Agent时有效")
    private String autoChatMsg;

     @Schema(description =  "消息内容是否必须，1必须，0非必须")
    private Integer msgRequire;

}
