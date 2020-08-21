package io.bytechat.confirm;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.client.Client;
import io.bytechat.tcp.entity.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * @author : denglinhai
 * @date : 10:02 2020/8/21
 */
@Slf4j
public class MsgTimeoutHandlerManager {

    private Client client;

    private Map<Long, Future> handlerMap;

    private MsgTimeoutExecutor executor;

    private MsgTimeoutHandlerManager(Client client){
        this.client = client;
        this.handlerMap = new ConcurrentHashMap<>();
        this.executor = MsgTimeoutExecutor.getInstance();
    }

    public static MsgTimeoutHandlerManager getInstance(){
        return Singleton.get(MsgTimeoutHandlerManager.class);
    }

    public void addTimeoutHandler(Packet packet){
        if (ObjectUtil.isNull(packet)){
            log.warn("packet is null");
            return;
        }
        //多少时间重发一次
        int delay = 3;
        if (!handlerMap.containsKey(packet.getId())){
            MsgTimeoutHandler handler = new MsgTimeoutHandler(client);
            Future<Packet> future= executor.scheduledExecute(delay, packet, handler);
            handlerMap.put(packet.getId(), future);
        }
        log.info("添加消息超时任务成功，当前超时任务数={}", handlerMap.size());
    }

    public void removeTimeoutHandler(Packet packet){
        if (ObjectUtil.isNull(packet)){
            log.warn("packet is null");
            return;
        }
        Future future = handlerMap.remove(packet.getId());

        if (ObjectUtil.isNotNull(future)){
            /**
             * cancel(false)只会取消线程池还未执行的任务
             */
            future.cancel(true);
        }
    }
}
