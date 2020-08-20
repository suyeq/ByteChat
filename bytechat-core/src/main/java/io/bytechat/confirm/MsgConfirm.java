package io.bytechat.confirm;

import io.bytechat.tcp.entity.Packet;

/**
 * @author : denglinhai
 * @date : 10:09 2020/8/20
 */
public interface MsgConfirm {

    /**
     * 开始监听某一个Msg
     */
    void startMonitorMsg(Packet packet);

    /**
     * 结束对某一个msg监控
     * @return
     */
    boolean endMonitorMsg(Packet packet);
}
