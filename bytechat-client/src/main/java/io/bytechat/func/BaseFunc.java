package io.bytechat.func;

import cn.hutool.core.lang.Assert;
import io.bytechat.client.Client;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    public Payload sendRequest(Packet packet){
        Payload payload;
        try{
            CompletableFuture<Packet> promise = client.sendRequest(packet);
            payload = promise.get(3, TimeUnit.SECONDS).getPayload();
        }catch (Exception e){
            payload = PayloadFactory.newErrorPayload(400, "执行命令超时");
            log.error("执行命令错误,cause={}", e.getCause());
        }
        return payload;
    }


}
