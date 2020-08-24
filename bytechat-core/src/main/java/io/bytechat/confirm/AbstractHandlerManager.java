package io.bytechat.confirm;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.client.Client;
import io.bytechat.executor.Executor;
import io.bytechat.tcp.entity.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @author : denglinhai
 * @date : 10:12 2020/8/24
 */
@Slf4j
public abstract class AbstractHandlerManager {

    protected Map<Long, Future> handlerMap;

    protected Executor executor;

    public AbstractHandlerManager(Executor executor){
        this.executor = executor;
        this.handlerMap = new ConcurrentHashMap<>();
    }

    /**
     * 增加handler处理
     * @param packet
     */
    public abstract void addHandler(Packet packet, Client client);

    public void removeHandler(Packet packet){
        if (ObjectUtil.isNull(packet)){
            log.warn("packet is null");
            return;
        }
        Future future = handlerMap.remove(packet.getId());

        if (ObjectUtil.isNotNull(future)){
            /**
             * cancel(false)只会取消线程池还未执行的任务
             */
            boolean isSuccess = future.cancel(true);
            if (isSuccess){
                log.info("取消超时任务成功,取消任务id={}，当前超时任务数={}", packet.getId(), handlerMap.size());
            }
        }
    }

    public boolean existHandler(Packet packet){
        return handlerMap.containsKey(packet.getId());
    }
}
