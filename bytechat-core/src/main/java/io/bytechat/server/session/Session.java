package io.bytechat.server.session;

import io.bytechat.server.channel.ChannelType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/**
 * @author : denglinhai
 * @date : 21:59 2020/2/26
 * 一次会话实体
 */
public interface Session {

    /**
     * 返回sessionId
     * @return
     */
    String sessionId();

    /**
     * 返回channelId
     * @return
     */
    ChannelId channelId();

    /**
     * 返回channelType
     * @return
     */
    ChannelType channelType();

    /**
     * 返回userId
     * @return
     */
    long userId();

    /**
     * 每个session绑定一个channel
     * @param channelId
     * @param userId
     */
    void bind(ChannelId channelId, long userId);

    /**
     * 写入数据
     * @param msg
     */
    void writeAndFlush(Object msg);


}
