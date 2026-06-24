//package com.xspaceagi.agent.web.ui.ws;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.xspaceagi.agent.core.adapter.agent.AgentContext;
//import com.xspaceagi.agent.core.adapter.agent.WriteChannel;
//import com.xspaceagi.agent.core.adapter.agent.parser.PlaceholderParser;
//import com.xspaceagi.agent.core.adapter.application.AgentApplicationService;
//import com.xspaceagi.agent.core.adapter.dto.AgentDto;
//import com.xspaceagi.agent.core.adapter.dto.TryReqDto;
//import com.xspaceagi.agent.core.adapter.dto.config.AgentConfigDto;
//import com.xspaceagi.agent.web.ui.ws.config.WebSocketConfiguration;
//import com.xspaceagi.system.spec.exception.BizException;
//import jakarta.websocket.*;
//import jakarta.websocket.server.PathParam;
//import jakarta.websocket.server.ServerEndpoint;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//@ServerEndpoint(value = "/api/try/ws/{appPath}/{agentPath}", configurator = WebSocketConfiguration.class)
//public class MessageChannel implements ApplicationContextAware {
//
//    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
//
//    private static ApplicationContext applicationContext;
//
//    private AgentApplicationService agentApplicationService;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MessageChannel.class);
//
//    private Session session;
//
//    // 收到消息
//    @OnMessage
//    public void onMessage(Session session, String message, @PathParam("appPath") String appPath, @PathParam("agentPath") String agentPath) {
//        try {
//            onMessage0(session, message, appPath, agentPath);
//        } catch (Exception e) {
//            try {
//                String eMsg = e.getMessage();
//                if (!JSON.isValid(eMsg)) {
//                    JSONObject msg = new JSONObject();
//                    msg.put("event", "chat-msg");
//                    msg.put("data", e.getMessage());
//                    msg.put("id", UUID.randomUUID().toString().replace("-", ""));
//                    session.getBasicRemote().sendText(msg.toJSONString());
//                } else {
//                    session.getBasicRemote().sendText(eMsg);
//                }
//            } catch (IOException ex) {
//                //
//            }
//            if (!(e instanceof BizException)) {
//                LOGGER.error("[websocket]异常", e);
//            }
//        }
//    }
//
//    private void onMessage0(Session session, String message, String appPath, String agentPath) {
//        LOGGER.info("[websocket] 收到消息：id={}，message={}, appPath={}, agentPath={}", this.session.getId(), message, appPath, agentPath);
//        if (!JSON.isValid(message)) {
//            JSONObject msg = new JSONObject();
//            msg.put("event", "keepAlive");
//            throw new BizException(msg.toJSONString());
//        }
//        TryReqDto tryReqDto = JSONObject.parseObject(message, TryReqDto.class);
//        if (StringUtils.isBlank(tryReqDto.getMessage())) {
//            JSONObject msg = new JSONObject();
//            msg.put("event", "keepAlive");
//            throw new BizException(msg.toJSONString());
//        }
//        Map<String, String> requestHeaders = (Map<String, String>) session.getUserProperties().get("requestHeaders");
//        AgentDto agentDto = null;//agentApplicationService.getAgentByPathWithApiKeyCheck(appPath, agentPath, requestHeaders.get("api-key"));
//        if (agentDto == null) {
//            JSONObject msg = new JSONObject();
//            msg.put("event", "chat-msg");
//            msg.put("data", "你似乎走错地方了");
//            msg.put("id", UUID.randomUUID().toString());
//            throw new BizException(msg.toJSONString());
//        }
//
//        tryReqDto.setAgentId(agentDto.getAgentId());
//        checkAgentPermission(tryReqDto.getAgentId());
//        AgentConfigDto agentConfigDto = agentApplicationService.queryById(tryReqDto.getAgentId());
//        AgentContext agentContext = buildAgentContext(agentConfigDto, tryReqDto, requestHeaders);
//        agentContext.setWriteChannel(new WriteChannel() {
//
//            private String id;
//
//            @Override
//            public void writeObject(Object obj) {
//
//            }
//
//            @Override
//            public void writeStream(String id, String type, String text, String ext) {
//                try {
//                    this.id = id;
//                    JSONObject msg = new JSONObject();
//                    msg.put("id", id);
//                    msg.put("event", type);
//                    msg.put("data", text);
//                    if (ext != null) {
//                        msg.put("ext", ext);
//                    }
//                    session.getBasicRemote().sendText(msg.toJSONString());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void complete(String data) {
//                LOGGER.info("[websocket] 消息接收完成 {}", JSONObject.toJSONString(agentContext.getExecuteLogs()));
//                try {
//                    JSONObject msg = new JSONObject();
//                    msg.put("id", id);
//                    msg.put("event", "chat-done");
//                    session.getBasicRemote().sendText(msg.toJSONString());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void close() {
//            }
//        });
//        LOGGER.info("[websocket] 开始执行 {}", agentContext.getAgentConfig().getName());
//        Object obj = null;//new DefaultAgentOrPluginHandler().execute(agentContext);
//        if (obj != null) {
//            try {
//                JSONObject msg = new JSONObject();
//                msg.put("event", "chat-msg");
//                msg.put("data", PlaceholderParser.parseString(obj));
//                msg.put("id", agentContext.getRequestId());
//                session.getBasicRemote().sendText(msg.toJSONString());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void checkAgentPermission(Long agentId) {
//        //TODO
//    }
//
//    // 连接打开
//    @OnOpen
//    public void onOpen(Session session, @PathParam("appPath") String appPath, @PathParam("agentPath") String agentPath) {
//        // 保存 session 到对象
//        this.session = session;
//        session.setMaxIdleTimeout(300000L);
//        agentApplicationService = applicationContext.getBean(AgentApplicationService.class);
//        sessions.put(session.getId(), session);
//        LOGGER.info("[websocket] 新的连接：id={}", this.session.getId());
//        Map<String, String> requestHeaders = (Map<String, String>) session.getUserProperties().get("requestHeaders");
//        AgentConfigDto agentDto = null;// agentApplicationService.getAgentByPathWithApiKeyCheck(appPath, agentPath, requestHeaders.get("api-key"));
//        try {
//            if (agentDto == null) {
//                JSONObject msg = new JSONObject();
//                msg.put("event", "chat-msg");
//                msg.put("data", "你似乎走错地方了");
//                msg.put("id", UUID.randomUUID().toString());
//                session.getBasicRemote().sendText(msg.toJSONString());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    // 连接关闭
//    @OnClose
//    public void onClose(CloseReason closeReason) {
//        sessions.remove(session.getId(), session);
//        LOGGER.info("[websocket] 连接断开：id={}，reason={}, 当前连接数：{}", this.session.getId(), closeReason, sessions.size());
//    }
//
//    // 连接异常
//    @OnError
//    public void onError(Throwable throwable) throws IOException {
//        sessions.remove(session.getId(), session);
//        LOGGER.info("[websocket] 连接异常：id={}，throwable={}", this.session.getId(), throwable.getMessage());
//        // 关闭连接。状态码为 UNEXPECTED_CONDITION（意料之外的异常）
//        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        MessageChannel.applicationContext = applicationContext;
//    }
//
//    private AgentContext buildAgentContext(AgentConfigDto agentConfigDto, TryReqDto tryReqDto, Map<String, String> headers) {
//        AgentContext agentContext = new AgentContext();
//        agentContext.setAgentConfig(agentConfigDto);
//        agentContext.setSessionId(tryReqDto.getSessionId());
//        agentContext.setRequestId(UUID.randomUUID().toString().replace("-", ""));
//        agentContext.setMessage(tryReqDto.getMessage());
//        agentContext.setAgentParams(tryReqDto.getParams());
//        agentContext.setAttachments(tryReqDto.getAttachments());
//        agentContext.setRequestHeaders(headers);
//        return agentContext;
//    }
//}