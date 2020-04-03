package io.bytechat.server.session;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.server.channel.ChannelType;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : denglinhai
 * @date : 16:31 2020/2/27
 */
@Slf4j
public abstract class AbstractSessionManager implements SessionManager{

    private static Map<String, Session> sessionMap;

    public AbstractSessionManager(){
        sessionMap = new ConcurrentHashMap<>(16);
    }

    @Override
    public Session newSession() {
        String sessionId = nextSessionId();
        return newSession(sessionId);
    }

    @Override
    public void bind(Session session, ChannelId channelId, long userId) {
        Assert.notNull(session, "session不能为空");
        Assert.notNull(channelId, "channelId不能为空");
        session.bind(channelId, userId);
        sessionMap.putIfAbsent(session.sessionId(), session);
        log.info("session={}绑定channelId={}成功", session, channelId);
    }

    @Override
    public void removeSession(ChannelId channelId) {
        Assert.notNull(channelId, "channelId不能为空");
        if (CollectionUtil.isEmpty(sessionMap)){
            return;
        }
        Iterator<Session> iterator = sessionMap.values().iterator();
        while (iterator.hasNext()){
            Session session = iterator.next();
            if (session.channelId().equals(channelId)){
                iterator.remove();
                log.info("移除session={}", session);
                break;
            }
        }
    }

    @Override
    public Session getSession(String sessionId) {
        Assert.notNull(sessionId, "sessionId 不能为空");
        return sessionMap.get(sessionId);
    }

    @Override
    public boolean exists(ChannelType channelType, long userId){
        List<Session> sessions = CollectionUtil.newArrayList(sessionMap.values());
        if (CollectionUtil.isEmpty(sessions)){
            return false;
        }
        Session session = sessions.stream().filter(e -> e.userId() == userId
                && e.channelType() == channelType).findFirst().orElse(null);
        return session != null;
    }

    @Override
    public Session getSessionByUserIdAndChannelType(long userId, ChannelType channelType){
        List<Session> sessions = CollectionUtil.newArrayList(sessionMap.values());
        if (CollectionUtil.isEmpty(sessions)){
            return null;
        }
        return sessions.stream().filter(e -> e.userId() == userId
                && e.channelType() == channelType).findFirst().orElse(null);
    }

    /**
     * 生成sessionId
     * @return
     */
    @Override
    public abstract String nextSessionId();

    /**
     * 具体创建session交由子类实现
     * @param sessionId
     * @return
     */
    public abstract Session newSession(String sessionId);


}
