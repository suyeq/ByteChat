package io.bytechat.server.channel;

import io.bytechat.server.ServerAttr;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

/**
 * @author : denglinhai
 * @date : 15:25 2020/2/26
 * channel监听
 */
public interface ChannelListener {

    /**
     * 用于添加到channel 管理器中
     * @param channel
     * @param channelType
     */
    void channelActive(Channel channel, ChannelType channelType);

    /**
     * 用于从channel管理器中删除channel
     * @param channelId
     */
    void channelInactive(ChannelId channelId);

    /**
     * 绑定serverAttr
     * @param serverAttr
     */
    void bindServerAttr(ServerAttr serverAttr);
}
