package io.bytechat.lang.config;

import io.bytechat.lang.exception.ConfigException;
import io.bytechat.lang.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : denglinhai
 * @date : 20:18 2020/2/25
 * 配置工厂类，生成对应的配置类
 */
@Slf4j
public class ConfigFactory {

    private static Map<Class<? extends Config>, Object> map = new HashMap<>();

    private ConfigFactory(){}

    public static <T extends Config> T getConfig(Class<? extends T> clazz){
        T config = (T) map.get(clazz);
        if (config == null){
            synchronized (ConfigFactory.class){
                config = (T) map.get(clazz);
                //再次判断用于防止其他阻塞线程唤醒后再次实例化config
                if (config == null){
                    config = org.aeonbits.owner.ConfigFactory.create(clazz);
                    if (config == null){
                        log.error("获取配置类{}失败",ThreadPoolConfig.class.getSimpleName());
                        throw new ConfigException(ExceptionEnum.CONFIG_EXCEPTION_NULL);
                    }
                    map.putIfAbsent(clazz, config);
                }
            }
        }
        return config;
    }

}
