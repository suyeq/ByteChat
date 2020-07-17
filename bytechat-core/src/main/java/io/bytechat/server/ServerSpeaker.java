package io.bytechat.server;

import io.bytechat.tcp.entity.Packet;

/**
 * @author : denglinhai
 * @date : 11:37 2020/5/9
 */
public interface ServerSpeaker {

    /**
     * transmit a command to another Server
     * @param serverAttr
     * @param packet
     * @return
     */
    Packet speak(ServerAttr serverAttr, Packet packet);
}
