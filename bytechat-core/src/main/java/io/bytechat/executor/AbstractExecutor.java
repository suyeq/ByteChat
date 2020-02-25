package io.bytechat.executor;

import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.config.ThreadPoolConfig;
import io.bytechat.lang.exception.ConfigException;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : denglinhai
 * @date : 18:41 2020/2/25
 */
@Slf4j
public abstract class AbstractExecutor<T> implements Executor<T>{

    private java.util.concurrent.Executor executor = null;

    public AbstractExecutor(){
        this(null);
    }

    public AbstractExecutor(java.util.concurrent.Executor eventExecutor){
        this.executor = eventExecutor == null ? ExecutorNewInstance.executor : eventExecutor;
    }

    @Override
    public T execute(Object... task){
        return doExecute(task);
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
    private static final class ExecutorNewInstance{
         private static ThreadPoolConfig config = ConfigFactory.getConfig(ThreadPoolConfig.class);
         private static java.util.concurrent.Executor executor = new ThreadPoolExecutor(config.corePoolSize(),
                 config.maxPoolSize(), config.keepAliveTime(), TimeUnit.SECONDS, new ArrayBlockingQueue<>(config.blockingQueueLength()),
                 new DefaultThreadFactory(config.threadName(), true));
    }
}
