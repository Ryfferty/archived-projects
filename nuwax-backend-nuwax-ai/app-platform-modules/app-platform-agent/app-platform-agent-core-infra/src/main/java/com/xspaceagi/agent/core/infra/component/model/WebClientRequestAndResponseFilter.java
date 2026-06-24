package com.xspaceagi.agent.core.infra.component.model;

import com.xspaceagi.system.spec.cache.SimpleJvmHashCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class WebClientRequestAndResponseFilter {


    public static ExchangeFilterFunction requestFilter = ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
        if (log.isDebugEnabled()) {
            // 打印原始HTTP请求
            StringBuilder stringBuilder = new StringBuilder(clientRequest.method().toString()).append(" ").append(clientRequest.url()).append("\n");
            HttpHeaders headers = clientRequest.headers();
            headers.forEach((name, values) -> values.forEach(value -> stringBuilder.append(name).append(": ").append(value).append("\n")));
            log.debug(stringBuilder.toString());
        }
        return Mono.just(clientRequest);
    });

    // 响应拦截器
    public static ExchangeFilterFunction responseFilter = ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
        log.debug("Intercepted Response Status: {}", clientResponse.statusCode());
        HttpRequest request = clientResponse.request();
        boolean isReasoning = false;
        List<String> reasonModelHeader = request.getHeaders().get("X-Reason-Model");
        if (CollectionUtils.isNotEmpty(reasonModelHeader)) {
            if ("1".equals(reasonModelHeader.get(0))) {
                isReasoning = true;
            }
        }
        log.debug("Intercepted Response URL: {} , isReasoning {}", request.getURI(), isReasoning);
        if (!isReasoning && !log.isDebugEnabled()) {
            return Mono.just(clientResponse);
        }
        return Mono.just(createModifiedResponseForReasoning(clientResponse));
    });

    /**
     * 处理推理请求的响应
     *
     * @param clientResponse
     * @return
     */
    private static ClientResponse createModifiedResponseForReasoning(ClientResponse clientResponse) {
        List<String> originalOutput = buildOutput(clientResponse);
        final AtomicBoolean think = new AtomicBoolean(false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 处理SSE事件流
        Flux<DataBuffer> modifiedBody = clientResponse.bodyToFlux(DataBuffer.class)
                .map(buf -> {
                    try {
                        byte[] bytes = new byte[buf.readableByteCount()];
                        buf.read(bytes);
                        DataBufferUtils.release(buf);
                        byteArrayOutputStream.write(bytes);
                        bytes = byteArrayOutputStream.toByteArray();
                        //将bytes以\n\n进行分割

                        byteArrayOutputStream.reset();
                        //判断bytes是不是\n\n结尾
                        if (bytes.length >= 2 && bytes[bytes.length - 2] == '\n' && bytes[bytes.length - 1] == '\n') {
                            return new String(bytes, StandardCharsets.UTF_8);
                        }
                        int start = 0;
                        for (int i = 0; i < bytes.length - 1; i++) {
                            // 检查当前字节和下一个字节是否为 \n\n
                            if (bytes[i] == 0x0A && bytes[i + 1] == 0x0A) {
                                // 提取从 start 到 i 的字节
                                byte[] part = new byte[i - start];
                                System.arraycopy(bytes, start, part, 0, part.length);
                                byteArrayOutputStream.write(part);
                                byteArrayOutputStream.write(0x0A);
                                byteArrayOutputStream.write(0x0A);
                                // 更新 start 到分割符之后
                                start = i + 2;
                                i++; // 跳过下一个字节（因为已经检查过了）
                            }
                        }
                        byte[] strBytes = byteArrayOutputStream.toByteArray();
                        // 添加最后一部分（如果没有剩余内容，则不会添加空数组）
                        if (start < bytes.length) {
                            byte[] lastPart = new byte[bytes.length - start];
                            System.arraycopy(bytes, start, lastPart, 0, lastPart.length);
                            byteArrayOutputStream.reset();
                            byteArrayOutputStream.write(lastPart);
                        }
                        return new String(strBytes, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        //  忽略
                    }
                    return "";
                }).flatMap(eventBodyString -> {
                    log.debug(eventBodyString);
                    originalOutput.add(eventBodyString);
                    return Mono.just(eventBodyString);
                })
                .map(str -> {
                    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                    return new DefaultDataBufferFactory().wrap(bytes);
                });

        // 创建新的ClientResponse，移除CONTENT_LENGTH头
        ClientResponse modifiedResponse = ClientResponse.from(clientResponse)
                .headers(headers -> headers.remove(HttpHeaders.CONTENT_LENGTH))
                .body(modifiedBody)
                .build();

        return modifiedResponse;
    }

    private static List<String> buildOutput(ClientResponse clientResponse) {
        try {
            List<String> originalOutput = null;
            List<String> requestIdHeaders = clientResponse.request().getHeaders().get("Request-Id");
            if (CollectionUtils.isNotEmpty(requestIdHeaders)) {
                Map<String, Map<String, Object>> modelExecuteInfos = (Map<String, Map<String, Object>>) SimpleJvmHashCache.getHash(requestIdHeaders.get(0), "modelExecuteInfos");
                if (modelExecuteInfos != null) {
                    List<String> traceIdHeaders = clientResponse.request().getHeaders().get("X-Trace-Id");
                    if (CollectionUtils.isNotEmpty(traceIdHeaders)) {
                        Map<String, Object> map = modelExecuteInfos.get(traceIdHeaders.get(0));
                        if (map != null) {
                            originalOutput = (List<String>) map.get("originalOutput");
                            if (originalOutput == null) {
                                originalOutput = new ArrayList<>();
                                map.put("originalOutput", originalOutput);
                            }
                        }
                    }
                }
            }
            return originalOutput == null ? new ArrayList<>() : originalOutput;
        } catch (Exception e) {
            log.error("buildOutput error", e);
            return new ArrayList<>();
        }
    }
}