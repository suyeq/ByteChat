package io.bytechat.confirm;

import cn.hutool.core.util.ObjectUtil;
import io.bytechat.client.Client;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 10:26 2020/8/24
 */
@Slf4j
public class MsgMonitorHandler implements Handler {

    private Client client;

    private MsgMonitorHandlerManager monitorManager;

    private MsgTimeoutHandlerManager timeoutManager;

    public MsgMonitorHandler(Client client){
        this.client = client;
        this.monitorManager = MsgMonitorHandlerManager.getInstance();
        this.timeoutManager = MsgTimeoutHandlerManager.getInstance();
    }


    @Override
    public Payload handle(Packet packet) {
        if (ObjectUtil.isNull(packet)){
            return PayloadFactory.newErrorPayload(400, "packet is null");
        }

        if (client.isClose()){
            if (monitorManager != null){
                monitorManager.removeHandler(packet);
            }
            return PayloadFactory.newErrorPayload(400, "client is closed");
        }

        if (monitorManager.existHandler(packet)) {
            log.info("未在指定时间内收到packetId={}的确认答复，开始超时重传", packet.getId());
            timeoutManager.addHandler(packet, client);
        }else {
            //这一步表明，消息已经收到确认答复
            log.info("消息 packet={}已收到答复", packet.toString());
        }

        return PayloadFactory.newSuccessPayload();
    }
}
