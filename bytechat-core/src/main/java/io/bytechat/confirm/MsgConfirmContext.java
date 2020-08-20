package io.bytechat.confirm;

import cn.hutool.core.lang.Assert;
import io.bytechat.tcp.entity.Packet;

/**
 * @author : denglinhai
 * @date : 16:09 2020/7/23
 */
public class MsgConfirmContext implements MsgConfirm{

    private MsgConfirmExecutor executor;

    public MsgConfirmContext(){
        executor = MsgConfirmExecutor.getInstance();
    }

    @Override
    public void startMonitorMsg(Packet packet) {
        Assert.notNull(packet, "packet is not null");
        executor.startMonitor(packet.getId());
    }

    @Override
    public boolean endMonitorMsg(Packet packet) {
        Assert.notNull(packet, "packet is not null");
        return executor.endMonitor(packet.getId());
    }
}
