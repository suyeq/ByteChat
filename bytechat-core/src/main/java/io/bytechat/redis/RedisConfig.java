package io.bytechat.redis;

import org.aeonbits.owner.Config;

/**
 * @author : denglinhai
 * @date : 12:40 2020/5/9
 */
@Config.Sources(value = "classpath:config/redis-config.properties")
public interface RedisConfig extends Config {

    /**
     * redis host
     * @return
     */
    String redisHost();

    /**
     * redis port
     * @return
     */
    int redisPort();

    /**
     * pwd
     * @return
     */
    String redisPassword();

    /**
     *
     * @return
     */
    int redisPoolMaxActive();

    int redisPoolMaxWait();

    int redisPoolMaxIdle();

    int redisPoolMinIdle();

    int redisTimeout();
}
