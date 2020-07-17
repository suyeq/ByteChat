package io.bytechat.redis;


import io.bytechat.lang.config.ConfigFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author : denglinhai
 * @date : 12:44 2020/5/9
 */
public class JedisService {

    private  JedisPool jedisPool;

    private static JedisService jedisService;

    private JedisService(){
        initJedisPool();
    }

    private void initJedisPool() {
        RedisConfig jedisConfig =  ConfigFactory.getConfig(RedisConfig.class);
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(jedisConfig.redisPoolMaxActive());
        config.setMaxIdle(jedisConfig.redisPoolMaxIdle());
        config.setMaxWaitMillis(jedisConfig.redisPoolMaxWait());
        String host = jedisConfig.redisHost();
        int port = jedisConfig.redisPort();
        int timeout = jedisConfig.redisTimeout();
        String password = jedisConfig.redisPassword();
        JedisPool pool=new JedisPool(config,host,port,timeout,password);
        this.jedisPool = pool;
    }

    public static JedisService getInstance(){
        if (jedisService == null){
            jedisService = new JedisService();
            return jedisService;
        }
        return jedisService;
    }

    public JedisPool getJedisPool(){
        return this.jedisPool;
    }
}
