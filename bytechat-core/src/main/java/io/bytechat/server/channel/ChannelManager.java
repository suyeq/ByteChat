package io.bytechat.server.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/**
 * @author : denglinhai
 * @date : 15:26 2020/2/26
 * channel管理
 */
public interface ChannelManager {

    /**
     * 添加channel
     * @param channel
     * @param channelType
     */
    void addChannel(Channel channel, ChannelType channelType);

    /**
     * 移除channel
     * @param channelId
     */
    void removeChannel(ChannelId channelId);

    /**
     * 获取channel包装类
     * @param channelId
     * @return
     */
    ChannelWrapper getChannelWrapper(ChannelId channelId);
}
