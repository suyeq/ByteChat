package io.bytechat.server;

import io.bytechat.tcp.entity.Packet;

/**
 * @author : denglinhai
 * @date : 13:29 2020/2/26
 * 服务器统一接口
 */
public interface Server {

    /**
     * 启动服务
     */
    void start();

    /**
     * 关闭服务
     */
    void stop();

    /**
     * 注册路由
     */
    void registerRouter(ServerAttr serverAttr);
}
