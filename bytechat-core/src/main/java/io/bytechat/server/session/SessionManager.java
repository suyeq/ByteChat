package io.bytechat.server.session;

import io.bytechat.server.channel.ChannelType;
import io.netty.channel.ChannelId;

/**
 * @author : denglinhai
 * @date : 16:10 2020/2/27
 * session管理
 */
public interface SessionManager {

    /**
     * 创建一个session
     * @return
     */
    Session newSession();

    /**
     * 生成sessionId
     * @return
     */
    String nextSessionId();

    /**
     * 绑定session与channel
     * @param session
     * @param channelId
     * @param userId
     */
    void bind(Session session, ChannelId channelId, long userId);

    /**
     * 移除session
     * @param channelId
     */
    void removeSession(ChannelId channelId);

    /**
     * 获取一个session
     * @param channelId
     * @return
     */
    Session getSession(ChannelId channelId);

    /**
     * 判断是否已绑定
     * @param channelType
     * @param userId
     * @return
     */
    boolean exists(ChannelType channelType, long userId);


}
