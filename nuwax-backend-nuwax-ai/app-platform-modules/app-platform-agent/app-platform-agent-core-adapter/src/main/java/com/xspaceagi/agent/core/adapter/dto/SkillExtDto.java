package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Skill extension config")
public class SkillExtDto implements Serializable {

    @Schema(description = "Support task agent: 0-no, 1-yes")
    private Integer supportTaskAgent;

    @Schema(description = "Support page app development: 0-no, 1-yes")
    private Integer supportPageApp;
}
