package io.bytechat.lang.exception;

/**
 * @author : denglinhai
 * @date : 22:54 2020/2/26
 */
public class BaseException extends RuntimeException{

    public BaseException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.toString());
    }

}
