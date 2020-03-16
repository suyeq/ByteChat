package io.bytechat.client;

/**
 * @author : denglinhai
 * @date : 20:42 2020/3/16
 */
public interface ClientFactory {

    /**
     * 新建一个单例客户端
     * @return
     */
    Client newClient();
}
