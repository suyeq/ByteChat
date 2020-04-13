package io.bytechat.lang.config;

import org.aeonbits.owner.Config;

/**
 * @author : denglinhai
 * @date : 16:07 2020/4/12
 */
@Config.Sources(value = "classpath:config/snowflake-config.properties")
public interface SnowFlakeConfig extends Config{

    /**
     * 服务工作者id
     * @return
     */
    @DefaultValue("1")
    long workerId();

    /**
     * 数据中心id
     * @return
     */
    @DefaultValue("1")
    long dataCenterId();
}
