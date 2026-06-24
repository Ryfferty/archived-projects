package com.xspaceagi.sandbox.infra.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;

/**
 * WebSocket Frame转换为ByteBuf的Handler
 * <p>
 * 该Handler作为透明转换层，位于WebSocket解码器和UserChannelHandler之间
 * 将WebSocket Frame转换为ByteBuf，使UserChannelHandler无感知地处理WebSocket数据
 * <p>
 * 数据流向：
 * WebSocket Frame -> WebSocketFrameToByteBufHandler -> ByteBuf -> UserChannelHandler
 *
 * @author sandbox
 */
@Slf4j
public class WebSocketFrameToByteBufHandler extends ChannelDuplexHandler {

    /**
     * WebSocket握手是否完成
     */
    private volatile boolean handshakeComplete = false;

    /**
     * 握手期间的待发送数据队列
     */
    private final java.util.Queue<Object> pendingWrites = new java.util.LinkedList<>();

    /**
     * 用于累加分片消息的缓冲区
     */
    private ByteBuf fragmentationBuffer;

    /**
     * 当前分片消息的类型（true为文本，false为二进制）
     */
    private Boolean isTextFragment;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            handshakeComplete = true;
            log.info("🤝 WebSocket handshake completed! Channel: {}, flushing {} pending messages...",
                    ctx.channel().id().asShortText(), pendingWrites.size());

            // 发送所有缓存的数据
            Object pendingMsg;
            int count = 0;
            while ((pendingMsg = pendingWrites.poll()) != null) {
                ctx.write(pendingMsg);
                count++;
            }

            // 批量flush
            if (count > 0) {
                ctx.flush();
                log.info("✅ Flushed {} pending messages after handshake", count);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof WebSocketFrame)) {
            // 非WebSocket Frame，直接传递给下一个handler
            log.trace("Passing non-WebSocket message: {}", msg.getClass().getSimpleName());
            ctx.fireChannelRead(msg);
            return;
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        log.trace("Received WebSocket frame: {}", frame.getClass().getSimpleName());

        try {
            if (frame instanceof CloseWebSocketFrame) {
                handleCloseFrame(ctx, (CloseWebSocketFrame) frame);
            } else if (frame instanceof PingWebSocketFrame) {
                handlePingFrame(ctx, (PingWebSocketFrame) frame);
            } else if (frame instanceof PongWebSocketFrame) {
                handlePongFrame(ctx, (PongWebSocketFrame) frame);
            } else if (frame instanceof TextWebSocketFrame) {
                handleTextFrame(ctx, (TextWebSocketFrame) frame);
            } else if (frame instanceof BinaryWebSocketFrame) {
                handleBinaryFrame(ctx, (BinaryWebSocketFrame) frame);
            } else if (frame instanceof ContinuationWebSocketFrame) {
                handleContinuationFrame(ctx, (ContinuationWebSocketFrame) frame);
            } else {
                log.warn("Unsupported WebSocket frame type: {}", frame.getClass().getName());
                frame.release();
            }
        } catch (Exception e) {
            log.error("Error processing WebSocket frame", e);
            frame.release();
            throw e;
        }
    }

    /**
     * 处理关闭帧
     * Close帧不应该传递给UserChannelHandler，而是直接关闭连接
     */
    private void handleCloseFrame(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
        log.info("Received Close WebSocket Frame, closing connection");
        try {
            // 回复Close帧
            ctx.writeAndFlush(frame.retain()).addListener(future -> {
                ctx.close();
                log.debug("WebSocket connection closed after receiving Close frame");
            });
        } catch (Exception e) {
            log.error("Error handling close frame", e);
            ctx.close();
        }
    }

    /**
     * 处理Ping帧
     */
    private void handlePingFrame(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
        log.debug("Received Ping WebSocket Frame");
        try {
            // 自动回复Pong帧
            ByteBuf content = frame.content();
            if (content.isReadable()) {
                ctx.writeAndFlush(new PongWebSocketFrame(content.retainedDuplicate()));
            } else {
                ctx.writeAndFlush(new PongWebSocketFrame());
            }
        } finally {
            frame.release();
        }
    }

    /**
     * 处理Pong帧
     */
    private void handlePongFrame(ChannelHandlerContext ctx, PongWebSocketFrame frame) {
        log.debug("Received Pong WebSocket Frame");
        // Pong帧不需要转发给UserChannelHandler
        frame.release();
    }

    /**
     * 处理文本帧
     */
    private void handleTextFrame(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            String text = frame.text();
            log.debug("Received Text WebSocket Frame, length: {}", text.length());

            // 检查是否为分片的最后一帧
            boolean isFinal = frame.isFinalFragment();

            if (!isFinal) {
                // 开始接收分片消息
                if (fragmentationBuffer == null) {
                    fragmentationBuffer = ctx.alloc().buffer();
                    isTextFragment = true;
                    log.debug("Start receiving text fragment");
                }
                // 累积分片数据
                fragmentationBuffer.writeBytes(text.getBytes(io.netty.util.CharsetUtil.UTF_8));
            } else {
                // 最后一帧或完整的消息
                if (fragmentationBuffer != null && Boolean.TRUE.equals(isTextFragment)) {
                    // 分片结束
                    fragmentationBuffer.writeBytes(text.getBytes(io.netty.util.CharsetUtil.UTF_8));
                    // 转发给UserChannelHandler
                    ByteBuf completeBuffer = fragmentationBuffer.retainedSlice();
                    ctx.fireChannelRead(completeBuffer);
                    log.debug("Completed text fragment, total length: {}", fragmentationBuffer.readableBytes());
                    // 释放并重置缓冲区
                    fragmentationBuffer.release();
                    fragmentationBuffer = null;
                    isTextFragment = null;
                } else {
                    // 单个完整的消息，转换为ByteBuf转发
                    byte[] textBytes = text.getBytes(io.netty.util.CharsetUtil.UTF_8);
                    ByteBuf buffer = Unpooled.wrappedBuffer(textBytes);
                    ctx.fireChannelRead(buffer);
                    log.debug("Forwarded text data as ByteBuf, length: {}", textBytes.length);
                }
            }
        } finally {
            frame.release();
        }
    }

    /**
     * 处理二进制帧
     */
    private void handleBinaryFrame(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) {
        try {
            ByteBuf binaryData = frame.content();
            int length = binaryData.readableBytes();
            log.debug("Received Binary WebSocket Frame, length: {}, final: {}", length, frame.isFinalFragment());

            // 检查是否为分片的最后一帧
            boolean isFinal = frame.isFinalFragment();

            if (!isFinal) {
                // 开始接收分片消息
                if (fragmentationBuffer == null) {
                    fragmentationBuffer = ctx.alloc().buffer();
                    isTextFragment = false;
                    log.info("Start receiving binary fragment");
                }
                // 累积分片数据
                fragmentationBuffer.writeBytes(binaryData);
                log.trace("Accumulated binary fragment, current size: {}", fragmentationBuffer.readableBytes());
            } else {
                // 最后一帧或完整的消息
                if (fragmentationBuffer != null && Boolean.FALSE.equals(isTextFragment)) {
                    // 分片结束
                    fragmentationBuffer.writeBytes(binaryData);
                    // 转发给UserChannelHandler
                    ByteBuf completeBuffer = fragmentationBuffer.retainedSlice();
                    log.info("Completed binary fragment, total length: {}, forwarding to UserChannelHandler", completeBuffer.readableBytes());
                    ctx.fireChannelRead(completeBuffer);
                    log.trace("Binary fragment successfully forwarded to UserChannelHandler");
                    // 释放并重置缓冲区
                    fragmentationBuffer.release();
                    fragmentationBuffer = null;
                    isTextFragment = null;
                } else {
                    // 单个完整的消息，直接转发
                    ByteBuf forwardBuffer = binaryData.retain();
                    log.info("Forwarding complete binary message to UserChannelHandler, length: {}", length);
                    ctx.fireChannelRead(forwardBuffer);
                    log.trace("Binary message successfully forwarded to UserChannelHandler");
                }
            }
        } finally {
            frame.release();
        }
    }

    /**
     * 处理续传帧（分片消息的中间帧）
     */
    private void handleContinuationFrame(ChannelHandlerContext ctx, ContinuationWebSocketFrame frame) {
        try {
            log.debug("Received Continuation WebSocket Frame");

            // 检查是否有正在进行的分片接收
            if (fragmentationBuffer == null) {
                log.warn("Received ContinuationFrame without active fragmentation, discarding");
                return;
            }

            ByteBuf continuationData = frame.content();
            boolean isFinal = frame.isFinalFragment();

            // 累加续传数据
            fragmentationBuffer.writeBytes(continuationData);

            if (isFinal) {
                // 分片结束，转发完整数据
                ByteBuf completeBuffer = fragmentationBuffer.retainedSlice();
                ctx.fireChannelRead(completeBuffer);
                log.debug("Completed continuation fragment, total length: {}, type: {}",
                        completeBuffer.readableBytes(), isTextFragment ? "TEXT" : "BINARY");
                // 释放并重置缓冲区
                fragmentationBuffer.release();
                fragmentationBuffer = null;
                isTextFragment = null;
            }
        } finally {
            frame.release();
        }
    }

    /**
     * 写数据时，将ByteBuf转换为WebSocket Frame
     * 这个方法会在Channel.write()时被调用
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 检查WebSocket握手是否完成
        if (!handshakeComplete) {
            String msgType = msg.getClass().getSimpleName();

            // 握手期间只缓存需要转换为WebSocket Frame的数据
            // HTTP相关消息（HttpResponse等）不缓存，让WebSocketServerProtocolHandler处理
            if (msg instanceof ByteBuf || msg instanceof byte[] || msg instanceof String) {
                log.debug("⏳ WebSocket handshake in progress, buffering message. Type: {}, Queue size: {}",
                        msgType, pendingWrites.size());

                Object convertedMsg = convertToWebSocketFrame(ctx, msg);
                if (convertedMsg != null) {
                    pendingWrites.offer(convertedMsg);
                    promise.setSuccess();
                    return;
                }
            }

            // HTTP消息（握手响应等）直接透传，不缓存
            log.trace("Passing through HTTP message during handshake: {}", msgType);
            ctx.write(msg, promise);
            return;
        }

        // 检查channel是否可写
        if (!ctx.channel().isWritable()) {
            log.warn("⚠️ Channel is NOT writable! Data will be buffered. Channel: {}, write buffer: {}",
                    ctx.channel().id().asShortText(), ctx.channel().bytesBeforeUnwritable());
        }

        try {
            Object convertedMsg = convertToWebSocketFrame(ctx, msg);
            if (convertedMsg != null) {
                // 关键修复：使用writeAndFlush而不是write
                ctx.writeAndFlush(convertedMsg, promise);
                log.trace("✅ WebSocket frame sent successfully, type: {}", convertedMsg.getClass().getSimpleName());
            } else {
                // 其他类型直接透传
                ctx.write(msg, promise);
            }
        } catch (Exception e) {
            log.error("❌ Error in write, channel: {}, isActive: {}", ctx.channel(), ctx.channel().isActive(), e);
            promise.setFailure(e);
            throw e;
        }
    }

    /**
     * 将消息转换为WebSocket Frame
     */
    private Object convertToWebSocketFrame(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            log.debug("📤 Convert ByteBuf to BinaryWebSocketFrame, length: {}, channel: {}",
                    byteBuf.readableBytes(), ctx.channel().id().asShortText());
            return new BinaryWebSocketFrame(byteBuf.retain());

        } else if (msg instanceof byte[]) {
            byte[] bytes = (byte[]) msg;
            log.debug("📤 Convert byte[] to BinaryWebSocketFrame, length: {}", bytes.length);
            return new BinaryWebSocketFrame(Unpooled.wrappedBuffer(bytes));

        } else if (msg instanceof String) {
            String text = (String) msg;
            log.debug("📤 Convert String to TextWebSocketFrame, length: {}", text.length());
            return new TextWebSocketFrame(text);

        } else if (msg instanceof BinaryWebSocketFrame || msg instanceof TextWebSocketFrame) {
            // 已经是WebSocket Frame，直接返回
            return msg;

        } else {
            log.trace("Passing through non-converted message: {}", msg.getClass().getSimpleName());
            return null;  // 表示不需要转换
        }
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("WebSocket to ByteBuf converter inactive");
        cleanupResources();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("WebSocket to ByteBuf converter exception", cause);
        cleanupResources();
        ctx.fireExceptionCaught(cause);
    }

    /**
     * 清理资源
     */
    private void cleanupResources() {
        if (fragmentationBuffer != null) {
            fragmentationBuffer.release();
            fragmentationBuffer = null;
        }
        isTextFragment = null;

        // 清空待发送队列
        if (!pendingWrites.isEmpty()) {
            log.warn("⚠️ Cleaning up {} pending messages due to channel inactive", pendingWrites.size());
            Object msg;
            while ((msg = pendingWrites.poll()) != null) {
                if (msg instanceof io.netty.util.ReferenceCounted) {
                    ((io.netty.util.ReferenceCounted) msg).release();
                }
            }
        }
    }
}