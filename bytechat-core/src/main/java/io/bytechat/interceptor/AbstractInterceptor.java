package io.bytechat.interceptor;

import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.netty.channel.Channel;

/**
 * @author : denglinhai
 * @date : 14:42 2020/4/17
 */
public abstract class AbstractInterceptor {

    /**
     * 预处理
     * @param packet
     * @param channel
     * @return
     */
    public abstract Payload preHandle(Packet packet, Channel channel);
}
