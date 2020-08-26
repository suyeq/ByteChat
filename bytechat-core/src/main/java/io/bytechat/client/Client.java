package io.bytechat.client;

import io.bytechat.tcp.entity.Packet;
import io.netty.util.concurrent.CompleteFuture;

import java.util.concurrent.CompletableFuture;

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
     * 断开连接
     */
    void closeConnect();

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

}
