package io.bytechat.client;

import cn.hutool.core.lang.Singleton;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ScheduledFuture;

/**
 * @author : denglinhai
 * @date : 10:21 2020/9/18
 * 重连任务检查
 */
public class ReconnectionChecker {

    private List<ScheduledFuture<?>> futures = new Vector<>();

    private ReconnectionChecker(){}

    public static ReconnectionChecker getInstance(){
        return Singleton.get(ReconnectionChecker.class);
    }

    /**
     * 检查所有重连任务，并全部取消
     * @return
     */
    public boolean handleConnection(){
        Iterator<ScheduledFuture<?>> iterator = futures.iterator();
        while (iterator.hasNext()){
            ScheduledFuture<?> future = iterator.next();
            boolean success = future.cancel(true);
            if (success){
                iterator.remove();
            }
        }
        return true;
    }

    public void joinHandle(ScheduledFuture<?> future){
        if (!futures.contains(future)){
            futures.add(future);
        }
    }
}
