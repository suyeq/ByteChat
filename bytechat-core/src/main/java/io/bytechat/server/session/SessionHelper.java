package io.bytechat.server.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 16:36 2020/3/31
 */
@Slf4j
public class SessionHelper {

    private final static AttributeKey<String> SESSION_ID = AttributeKey.newInstance("SESSION_ID");

    private SessionHelper(){

    }

    /**
     * channel绑定session,判定上线
     * @param channel
     * @param sessionId
     */
    public static void makeOnline(Channel channel, String sessionId){
        channel.attr(SESSION_ID).set(sessionId);
        log.info("[sessionId]={}上线了", sessionId);
    }

    /**
     * 根据channel返回sessionId
     * @param channel
     * @return
     */
    public static String getSessionId(Channel channel){
        return channel.hasAttr(SESSION_ID) ? channel.attr(SESSION_ID).get() : null;
    }
}
