package io.bytechat.client;

import io.bytechat.tcp.entity.Packet;

import java.util.concurrent.CompletableFuture;

/**
 * @author : denglinhai
 * @date : 20:31 2020/3/16
 */
public interface Client {

    /**
     * 连接服务器
     */
    boolean connect();

    /**
     * 断开连接
     */
    void closeConnect();

    /**
     * 连接状态重置
     */
    void connectionStateReset();

    /**
     * 是否关闭
     * @return
     */
    boolean isClose();

    /**
     * 消息已送达
     */
    void messageDelivery(Packet packet);

    /**
     * 发送请求
     * @param request
     * @return
     */
    CompletableFuture<Packet> sendRequest(Packet request);

    /**
     * 发送请求
     * @param request
     * @param isJoinMsgMonitor 是否加入消息监听
     * @return
     */
    CompletableFuture<Packet> sendRequest(Packet request, boolean isJoinMsgMonitor);

}
