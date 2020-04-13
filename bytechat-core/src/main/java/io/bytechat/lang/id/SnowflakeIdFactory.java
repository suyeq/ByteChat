package io.bytechat.lang.id;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.config.SnowFlakeConfig;

/**
 * @author : denglinhai
 * @date : 16:10 2020/4/12
 */
public class SnowflakeIdFactory implements IdFactory {

    private Snowflake snowflake;

    private SnowflakeIdFactory() {
        this(null);
    }

    private SnowflakeIdFactory(Long workerId) {
        SnowFlakeConfig config = ConfigFactory.getConfig(SnowFlakeConfig.class);
        Long realWorkerId = workerId != null ? workerId : config.workerId();
        this.snowflake = IdUtil.createSnowflake(realWorkerId, config.dataCenterId());
    }

    public static IdFactory getInstance() {
        return Singleton.get(SnowflakeIdFactory.class);
    }

    public static IdFactory getInstance(Long workerId) {
        return Singleton.get(SnowflakeIdFactory.class, workerId);
    }


    @Override
    public long nextId() {
        return snowflake.nextId();
    }
}
