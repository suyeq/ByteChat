package io.bytechat.lang.exception;

/**
 * @author : denglinhai
 * @date : 21:03 2020/2/25
 * 配置异常类
 */
public class ConfigException extends BaseException{

    public ConfigException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }

}
