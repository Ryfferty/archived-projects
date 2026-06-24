//package com.xspaceagi.agent.web.ui.ws.config;
//
//import com.xspaceagi.agent.web.ui.ws.MessageChannel;
//import jakarta.websocket.HandshakeResponse;
//import jakarta.websocket.server.HandshakeRequest;
//import jakarta.websocket.server.ServerEndpointConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//public class WebSocketConfiguration extends ServerEndpointConfig.Configurator {
//
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//
//        ServerEndpointExporter exporter = new ServerEndpointExporter();
//
//        // 手动注册 WebSocket 端点
//        exporter.setAnnotatedEndpointClasses(MessageChannel.class);
//
//        return exporter;
//    }
//
//    @Override
//    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//        // 这个userProperties 可以通过 session.getUserProperties()获取
//        final Map<String, Object> userProperties = sec.getUserProperties();
//        Map<String, String> headers = new HashMap<>();
//        for (String key : request.getHeaders().keySet()) {
//            List<String> values = request.getHeaders().get(key);
//            if (values.size() > 0) {
//                headers.put(key, values.get(0));
//            }
//        }
//        userProperties.put("requestHeaders", headers);
//    }
//
//    @Override
//    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
//        return super.getEndpointInstance(clazz);
//    }
//}