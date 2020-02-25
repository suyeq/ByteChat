package io.bytechat.executor;

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
}
