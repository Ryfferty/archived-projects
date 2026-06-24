package com.xspaceagi.custompage.infra.proxy;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理服务端 channel.
 */
public class HttpProxyResponseHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpProxyResponseHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel nextChannel = ctx.channel().attr(ProxyServerContainer.nextChannelAttributeKey).get();
        if (nextChannel != null) {
            String token = nextChannel.attr(ProxyServerContainer.tokenKey).get();
            if (token != null && (msg instanceof DefaultHttpResponse)) {
                DefaultHttpResponse response = (DefaultHttpResponse) msg;
                response.headers().set("Set-Cookie", "ticket=" + token + "; Path=/; HttpOnly; SameSite=None; Secure");
                nextChannel.attr(ProxyServerContainer.tokenKey).set(null);
            }
            // 转发时由目标 channel 的 pipeline（HttpObjectEncoder）在编码完成后释放，此处不再 release 避免重复释放
            nextChannel.writeAndFlush(msg);
        } else {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().attr(ProxyServerContainer.nextChannelAttributeKey).get() != null) {
            try {
                ctx.channel().attr(ProxyServerContainer.nextChannelAttributeKey).get()
                        .writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel().attr(ProxyServerContainer.nextChannelAttributeKey).get();
        if (channel != null) {
            channel.config().setOption(ChannelOption.AUTO_READ, ctx.channel().isWritable());
        }
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("exception caught", cause);
        super.exceptionCaught(ctx, cause);
    }
}