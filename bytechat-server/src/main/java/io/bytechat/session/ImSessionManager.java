package io.bytechat.session;

import cn.hutool.core.util.IdUtil;
import io.bytechat.server.session.AbstractSessionManager;
import io.bytechat.server.session.Session;

/**
 * @author : denglinhai
 * @date : 17:12 2020/3/29
 */
public class ImSessionManager extends AbstractSessionManager {

    private ImSessionManager(){

    }

    public static ImSessionManager getInstance(){
        return new ImSessionManager();
    }

    @Override
    public String nextSessionId() {
        return IdUtil.objectId();
    }

    @Override
    public Session newSession(String sessionId) {
        return new ImSession(sessionId);
    }
}
