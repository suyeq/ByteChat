package io.bytechat.func;

import cn.hutool.core.lang.Assert;
import io.bytechat.client.Client;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author : denglinhai
 * @date : 21:16 2020/3/31
 */
@Slf4j
public class BaseFunc {

    private Client client;

    public BaseFunc(Client client){
        Assert.notNull(client, "client 不能为空");
        this.client = client;
    }

    public Payload sendRequest(Packet packet, boolean isJoinMsgMonitor){
        Payload payload;
        try{
            CompletableFuture<Packet> promise = client.sendRequest(packet, isJoinMsgMonitor);
            payload = promise.get(3, TimeUnit.SECONDS).getPayload();
        }catch (TimeoutException e){
            payload = PayloadFactory.newSuccessPayload();
            //TODO :模拟用户选择是否重发时间
//            if (true){
//                client.sendRequest(packet);
//            }
        }catch (Exception e){
            payload = PayloadFactory.newErrorPayload(400, "execute command error");
            log.error("执行命令错误,cause={}", e.getCause());
        }
        return payload;
    }

    public void closeConnection(){
        client.closeConnect();
    }


}
