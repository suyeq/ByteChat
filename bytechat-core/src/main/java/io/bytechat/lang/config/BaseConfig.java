package io.bytechat.lang.config;

import org.aeonbits.owner.Config;

/**
 * @author : denglinhai
 * @date : 19:31 2020/2/25
 * 基础配置类
 */
@Config.Sources(value = "classpath:config/bytechat-base-config.properties")
public interface BaseConfig extends Config {

    /**
     * 基础扫描包
     * @return
     */
    @DefaultValue("io.bytechat")
    String basePackage();

    /**
     * 服务启动端口
     * @return
     */
    @DefaultValue("8848")
    int serverPort();
}
