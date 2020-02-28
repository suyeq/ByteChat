package io.bytechat;

import cn.hutool.core.lang.Assert;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.config.ThreadPoolConfig;
import io.bytechat.lang.exception.ConfigException;
import io.bytechat.lang.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import javax.sound.midi.SoundbankResource;

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
        try {
            throw new ConfigException(ExceptionEnum.CONFIG_EXCEPTION_NULL);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("lala");
    }
}
