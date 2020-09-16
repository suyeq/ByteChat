package io.bytechat.confirm;

import io.bytechat.client.Client;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 10:01 2020/8/21
 */
@Slf4j
public class MsgTimeoutHandler implements Handler{

    private Client client;

    private Packet packet;

    private MsgMonitorHandlerManager monitorManager;

    /**
     * 重发次数
     */
    private int currentReconCount;

    public MsgTimeoutHandler(Client client){
        this.client = client;
        this.currentReconCount = 0;
        this.monitorManager = MsgMonitorHandlerManager.getInstance();
    }

    @Override
    public Payload handle(Packet packet) {
        if (packet == null){
            return PayloadFactory.newErrorPayload(400, "消息为空");
        }
        this.packet = packet;
        if (client.isClose()){
            if (monitorManager != null){
                monitorManager.removeHandler(packet);
            }
            return PayloadFactory.newErrorPayload(400, "客户端已关闭");
        }
        currentReconCount++;
        //重连次数达上限
        if (currentReconCount > 3){
            try{
                //通知发送消息失败
            }finally {
                if (monitorManager != null){
                    monitorManager.removeHandler(packet);
                }
                //重连，到这一步可认定客户端已断开连接
                //client.connect();
                currentReconCount = 0;
            }
        }else {
            sendMsg();
        }
        return PayloadFactory.newSuccessPayload();
    }

    public void sendMsg(){
        client.sendRequest(packet);
        log.info("开始重发消息，msg={}", packet.toString());
    }
}
