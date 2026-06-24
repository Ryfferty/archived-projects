package com.xspaceagi.sandbox.infra.network.protocol;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Queue;

public interface Constants {

    AttributeKey<Channel> NEXT_CHANNEL = AttributeKey.newInstance("nxt_channel");

    AttributeKey<String> USER_ID = AttributeKey.newInstance("user_id");

    AttributeKey<String> CLIENT_KEY = AttributeKey.newInstance("client_key");

    AttributeKey<Queue<Object>> MESSAGE_QUEUE = AttributeKey.newInstance("message_queue");

}
