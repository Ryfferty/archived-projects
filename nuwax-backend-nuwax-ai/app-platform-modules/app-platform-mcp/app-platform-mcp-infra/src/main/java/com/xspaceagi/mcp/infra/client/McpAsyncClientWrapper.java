package com.xspaceagi.mcp.infra.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class McpAsyncClientWrapper {

    private HttpClientSseClientTransport httpClientSseClientTransport;

    private McpAsyncClient client;

    public void close() {
        if (client != null) {
            client.close();
        }
        if (httpClientSseClientTransport != null) {
            httpClientSseClientTransport.close();
        }
    }

    public Mono<Void> closeGracefully() {
        return Mono.create(sink -> {
            if (client != null) {
                client.close();
            }
            if (httpClientSseClientTransport != null) {
                httpClientSseClientTransport.close();
            }
            sink.success();
        });
    }
}
