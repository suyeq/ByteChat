package io.bytechat.server.session;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.IdUtil;

/**
 * @author : denglinhai
 * @date : 17:30 2020/2/27
 */
public class DefaultSessionManager extends AbstractSessionManager {

    private DefaultSessionManager(){

    }

    public static DefaultSessionManager newInstance(){
        return Singleton.get(DefaultSessionManager.class );
    }

    @Override
    public String nextSessionId() {
        return IdUtil.objectId();
    }

    @Override
    public Session newSession(String sessionId) {
        return new DefaultSession(sessionId);
    }
}
