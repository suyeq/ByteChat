package io.bytechat.executor;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;



/**
 * @author : denglinhai
 * @date : 18:38 2020/2/25
 */
public interface Executor<T> {

    /**
     * 执行request请求与response请求的执行方法
     * @param task 任务
     * @return
     */
    T execute(Object... task);

    /**
     * 异步执行任务
     * @param promise
     * @param request
     * @return
     */
    Future<T> asyncExecute(Promise<T> promise, Object... request);

    /**
     * delay excute task
     * @param request
     * @return
     */
    void scheduledExecute(Object... request);
}
