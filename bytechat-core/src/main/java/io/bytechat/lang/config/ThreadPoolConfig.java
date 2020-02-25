package io.bytechat.lang.config;
import org.aeonbits.owner.Config;

/**
 * @author : denglinhai
 * @date : 20:45 2020/2/25
 * 线程池配置参数接口
 */
@Config.Sources(value = "classpath:config/bytechat-thread-pool-config.properties")
public interface ThreadPoolConfig extends Config{

    /**
     * 核心线程数
     * @return
     */
    @DefaultValue("10")
    int corePoolSize();

    /**
     * 最大线程数
     * @return
     */
    @DefaultValue("20")
    int maxPoolSize();

    /**
     * 空闲线程最大存活时长
     * @return
     */
    @DefaultValue("10")
    int keepAliveTime();

    /**
     * 阻塞队列长度
     * @return
     */
    @DefaultValue("100")
    int blockingQueueLength();

    /**
     * 线程名称
     * @return
     */
    @DefaultValue("business-executor-pool")
    String threadName();
}
