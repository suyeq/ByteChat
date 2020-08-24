package io.bytechat.confirm;

import cn.hutool.core.lang.Singleton;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import io.bytechat.executor.AbstractExecutor;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.PayloadFactory;

import java.util.concurrent.Executor;

/**
 * @author : denglinhai
 * @date : 10:03 2020/8/21
 */
public class MsgTimeoutExecutor extends AbstractExecutor<Packet> {

    private MsgTimeoutExecutor() {
        super(ConfirmExecutorNewInstance.executor);
    }

    public static MsgTimeoutExecutor getInstance(){
        return Singleton.get(MsgTimeoutExecutor.class);
    }

    @Override
    public Packet doExecute(Object... task) {
        Packet packet = (Packet) task[1];
        Handler handler = (Handler) task[2];
        Payload payload = handler.handle(packet);
        return PacketFactory.newResponsePacket(payload, packet.getId());
    }
}
