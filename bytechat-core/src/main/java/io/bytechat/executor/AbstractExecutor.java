package io.bytechat.executor;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Func;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.config.ThreadPoolConfig;
import io.bytechat.lang.exception.ConfigException;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author : denglinhai
 * @date : 18:41 2020/2/25
 */
@Slf4j
public abstract class AbstractExecutor<T> implements Executor<T>{

    private java.util.concurrent.Executor executor;

    public AbstractExecutor(java.util.concurrent.Executor eventExecutor){
        Assert.notNull(eventExecutor, "executor is not null");
        this.executor = eventExecutor ;
    }

    @Override
    public T execute(Object... task){
        return doExecute(task);
    }

    @Override
    public Future<T> asyncExecute(Promise<T> promise, Object... request){
        if (promise == null){
            throw new NullPointerException("promise 不能为空");
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    T response = doExecute(request);
                    promise.setSuccess(response);
                }catch (Exception e){
                    promise.setFailure(e);
                }
            }
        });
        return promise;
    }

    @Override
    public ScheduledFuture<T> scheduledExecute(Object... request){
        Long delay = (Long) request[0];
        java.util.concurrent.ScheduledFuture scheduledFuture =
                ((ScheduledThreadPoolExecutor)executor).schedule(new Runnable() {
            @Override
            public void run() {
                doExecute(request);
            }
        }, delay, TimeUnit.MILLISECONDS);
        return scheduledFuture;
    }

    @Override
    public ScheduledFuture<T> scheduleRateExecute(Object... request) {
        Long delay = (Long) request[0];
        java.util.concurrent.ScheduledFuture scheduledFuture =
                ((ScheduledThreadPoolExecutor)executor).scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        doExecute(request);
                    }
                }, 0, delay, TimeUnit.MILLISECONDS);
        return scheduledFuture;
    }

    /**
     * 将具体执行器执行的具体步骤交由子类实现
     * @param task
     * @return
     */
    public abstract T doExecute(Object... task);

    /**
     * 实例化一个线程池，用于业务场景
     * @return
     */
    protected static final class BusinessExecutorNewInstance{
         private static ThreadPoolConfig config = ConfigFactory.getConfig(ThreadPoolConfig.class);
         public static java.util.concurrent.Executor executor = new ThreadPoolExecutor(config.corePoolSize(),
                 config.maxPoolSize(), config.keepAliveTime(), TimeUnit.SECONDS, new ArrayBlockingQueue<>(config.blockingQueueLength()),
                 new DefaultThreadFactory(config.threadName(), true));
    }

    /**
     * msg confirm executor
     */
    protected static final class ConfirmExecutorNewInstance{
        private static ThreadPoolConfig config = ConfigFactory.getConfig(ThreadPoolConfig.class);
        public static java.util.concurrent.Executor executor = new ScheduledThreadPoolExecutor(6);
    }
}
