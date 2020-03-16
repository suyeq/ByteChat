package io.bytechat.client;

import io.bytechat.tcp.entity.Packet;

/**
 * @author : denglinhai
 * @date : 20:31 2020/3/16
 */
public interface Client {

    /**
     * 连接服务器
     */
    void connect();

    /**
     * 发送请求
     * @param packet
     */
    void sendRequest(Packet packet);

}
