package com.xspaceagi.agent.web.ui.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ReferUtil {
    private static final String pattern = "(.*)/page/(\\w+)(?:-(\\d+))?/(\\w+)/?(.*)";

    public static RefererParseVo parseRefer(HttpServletRequest request) {
        String refer = request.getHeader("Referer");
        if (request.getAttribute("referer") != null) {
            refer = (String) request.getAttribute("referer");
        }
        RefererParseVo refererParseVo = new RefererParseVo();
        Pattern regex = Pattern.compile(pattern);
        if (refer != null) {
            Matcher matcher = regex.matcher(refer);
            if (matcher.matches()) {
                try {
                    refererParseVo.setPageId(Long.parseLong(matcher.group(2)));
                    refererParseVo.setAgentId(Long.parseLong(matcher.group(3)));
                    refererParseVo.setEnv(matcher.group(4));
                } catch (Exception e) {
                    // ignore
                    log.warn("parseRefer error: {}", e.getMessage());
                }
            }
        }
        return refererParseVo;
    }

    @Data
    public static class RefererParseVo {
        private Long agentId;
        private Long pageId;
        private String env;
    }
}
