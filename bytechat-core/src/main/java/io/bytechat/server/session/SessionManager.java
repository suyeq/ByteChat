package io.bytechat.server.session;

import io.bytechat.server.ServerAttr;
import io.bytechat.server.channel.ChannelType;
import io.netty.channel.ChannelId;

import java.util.List;

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
     * @param sessionId
     * @return
     */
    Session getSession(String sessionId);


    /**
     * 获取一个session
     * @param userId
     * @return
     */
    Session getSession(Long userId);

    /**
     * 判断是否已绑定
     * @param channelType
     * @param userId
     * @return
     */
    boolean exists(ChannelType channelType, long userId);

    /**
     * 根据userId与channelType寻找session
     * @param userId
     * @param channelType
     * @return
     */
    Session fetchSessionByUserIdAndChannelType(long userId, ChannelType channelType);

    /**
     * 获取所有的session
     * @return
     */
    List<Session> fetchAllSession();

    /**
     * bind serverAttr
     * @param serverAttr
     */
    void bindServerAttr(ServerAttr serverAttr);

}
