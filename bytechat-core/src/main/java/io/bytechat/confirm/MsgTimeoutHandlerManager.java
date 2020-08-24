package io.bytechat.confirm;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.client.Client;
import io.bytechat.executor.AbstractExecutor;
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
public class MsgTimeoutHandlerManager extends AbstractHandlerManager {

    private MsgTimeoutHandlerManager(Client client){
        super(MsgTimeoutExecutor.getInstance());
    }

    public static MsgTimeoutHandlerManager getInstance(){
        return Singleton.get(MsgTimeoutHandlerManager.class);
    }

    @Override
    public void addHandler(Packet packet, Client client) {
        if (ObjectUtil.isNull(packet)){
            log.warn("packet is null");
            return;
        }
        //多少时间重发一次
        int delay = 3;
        if (!handlerMap.containsKey(packet.getId())){
            MsgTimeoutHandler handler = new MsgTimeoutHandler(client);
            Future<Packet> future= executor.scheduleRateExecute(delay, packet, handler);
            handlerMap.put(packet.getId(), future);
        }
        log.info("添加消息超时任务成功，当前超时任务数={}", handlerMap.size());
    }
}
