package io.bytechat.confirm;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.client.Client;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;

/**
 * @author : denglinhai
 * @date : 10:27 2020/8/24
 */
@Slf4j
public class MsgMonitorHandlerManager extends AbstractHandlerManager {

    private MsgTimeoutHandlerManager timeoutManager;

    private MsgMonitorHandlerManager(){
        super(MsgTimeoutExecutor.getInstance());
        timeoutManager = MsgTimeoutHandlerManager.getInstance();
    }

    public static MsgMonitorHandlerManager getInstance(){
        return Singleton.get(MsgMonitorHandlerManager.class);
    }

    @Override
    public void addHandler(Packet packet, Client client) {
        if (ObjectUtil.isNull(packet)){
            log.warn("packet is null");
            return;
        }
        //排除消息确认请求
        Request request = packet.getRequest();
        if (request.isOnlyAck()){
            return;
        }
        //监听多长时间
        int delay = 3;
        if (!handlerMap.containsKey(packet.getId())){
            MsgMonitorHandler handler = new MsgMonitorHandler(client);
            Future<Packet> future= executor.scheduledExecute(delay, packet, handler);
            handlerMap.put(packet.getId(), future);
        }
        log.info("添加消息监听任务成功，当前监听任务数={}", handlerMap.size());
    }

    /**
     * 覆写，不仅需要移除monitorHandler
     * 还需要移除timeoutHandler
     * @param packet
     */
    @Override
    public void removeHandler(Packet packet) {
        super.removeHandler(packet);
        if (timeoutManager.existHandler(packet)){
            timeoutManager.removeHandler(packet);
        }
    }
}
