package io.bytechat;

import cn.hutool.core.lang.Assert;
import io.bytechat.init.Initializer;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.config.ThreadPoolConfig;
import io.bytechat.lang.exception.ConfigException;
import io.bytechat.lang.exception.ExceptionEnum;
import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.SnowflakeIdFactory;
import lombok.extern.slf4j.Slf4j;

import javax.sound.midi.SoundbankResource;
import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
@Slf4j
public class App 
{
    public static void main( String[] args )
    {
//        ThreadPoolConfig config = ConfigFactory.getConfig(ThreadPoolConfig.class);
//        System.out.println(config.keepAliveTime());
        //Assert.isNull(new Object(),"alal");
//        try {
//            throw new ConfigException(ExceptionEnum.CONFIG_EXCEPTION_NULL);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println("lala");
//        System.out.println(0x1);
//        Initializer.init();
//        IdFactory idFactory = SnowflakeIdFactory.getInstance();
//        System.out.println(idFactory.nextId());
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);
        System.out.println(inetSocketAddress.getAddress().getHostName());
        System.out.println(inetSocketAddress.getAddress().getHostAddress());
        System.out.println(inetSocketAddress.getHostName());
        System.out.println(inetSocketAddress.getPort());
        System.out.println(inetSocketAddress.getHostString());
    }
}
