package io.bytechat.confirm;

import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;

/**
 * @author : denglinhai
 * @date : 10:10 2020/8/24
 */
public interface Handler {

    /**
     * 处理
     * @param packet
     * @return
     */
    public Payload handle(Packet packet);
}
