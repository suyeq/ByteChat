package io.bytechat.lang.exception;

/**
 * @author : denglinhai
 * @date : 21:03 2020/2/25
 * 配置异常类
 */
public class ConfigException extends RuntimeException{

    private static String errorMsg = "获取配置类异常";

    public ConfigException(){
        super(errorMsg);
    }

}
