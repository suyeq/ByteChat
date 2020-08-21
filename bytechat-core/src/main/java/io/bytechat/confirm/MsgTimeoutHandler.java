package io.bytechat.confirm;

import io.bytechat.client.Client;
import io.bytechat.executor.Executor;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;

/**
 * @author : denglinhai
 * @date : 10:01 2020/8/21
 */
public class MsgTimeoutHandler{

    private Client client;

    private Packet packet;

    private MsgTimeoutHandlerManager manager;

    /**
     * 重发次数
     */
    private int currentReconCount;

    public MsgTimeoutHandler(Client client){
        this.client = client;
        this.currentReconCount = 0;
        this.manager = MsgTimeoutHandlerManager.getInstance();
    }

    public Payload handle(Packet packet) {
        if (packet == null){
            return PayloadFactory.newErrorPayload(400, "消息为空");
        }
        this.packet = packet;
        if (client.isClose()){
            if (manager != null){
                manager.remove(packet);
            }
            return PayloadFactory.newErrorPayload(400, "客户端已关闭");
        }
        currentReconCount++;
        if (currentReconCount > 3){
            try{
                //通知发送消息失败
            }finally {
                if (manager != null){
                    manager.remove(packet);
                }
                //重连，到这一步可认定客户端已断开连接
                client.connect();
                currentReconCount = 0;
            }
        }else {
            sendMsg();
        }
        return PayloadFactory.newSuccessPayload();
    }

    public void sendMsg(){
        client.sendRequest(packet);
    }
}
