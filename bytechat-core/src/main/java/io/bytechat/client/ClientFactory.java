package io.bytechat.client;

import io.bytechat.server.ServerAttr;

/**
 * @author : denglinhai
 * @date : 20:42 2020/3/16
 */
public interface ClientFactory {

    /**
     * 新建一个单例客户端
     * @param serverAttr
     * @return
     */
    Client newClient(ServerAttr serverAttr);
}
