package io.bytechat;

import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.config.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
public class App 
{
    public static void main( String[] args )
    {
        ThreadPoolConfig config = ConfigFactory.getConfig(ThreadPoolConfig.class);
        System.out.println(config.keepAliveTime());
    }
}
