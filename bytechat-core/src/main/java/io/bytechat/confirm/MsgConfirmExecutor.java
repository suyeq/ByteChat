package io.bytechat.confirm;

import cn.hutool.core.lang.Singleton;
import io.bytechat.executor.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : denglinhai
 * @date : 11:52 2020/7/23
 */
@Slf4j
public class MsgConfirmExecutor extends AbstractExecutor {

    private Map<Long, Boolean> msgMark;

    private MsgConfirmExecutor() {
        super(ConfirmExecutorNewInstance.executor);
        msgMark = new ConcurrentHashMap<>(128);
    }

    public static MsgConfirmExecutor getInstance(){
        return Singleton.get(MsgConfirmExecutor.class);
    }

    @Override
    public Object doExecute(Object... request) {
        Long msgId = (Long) request[0];
        Long delay = (Long) request[1];
        if (msgMark.containsKey(msgId)){
            scheduledExecute(delay, request);
        }else {
            log.info("{} msg confirm", msgId);
        }
        return null;
    }

    public void startMonitor(Long messageId){
        msgMark.put(messageId, true);
        scheduledExecute(messageId, 5 * 1000);
    }

    public boolean endMonitor(Long messageId){
        return msgMark.remove(messageId);
    }
}
