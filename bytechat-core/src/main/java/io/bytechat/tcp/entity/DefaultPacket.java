package io.bytechat.tcp.entity;

import io.bytechat.serialize.SerializeAlgorithmEnum;

/**
 * @author : denglinhai
 * @date : 17:07 2020/2/28
 */
public class DefaultPacket extends Packet {

    @Override
    public byte algorithm() {
        return SerializeAlgorithmEnum.FAST_JSON.getAlgorithm();
    }

    @Override
    public boolean isAsyncHandle() {
        return false;
    }
}
