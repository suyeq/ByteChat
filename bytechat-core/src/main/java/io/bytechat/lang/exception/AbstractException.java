package io.bytechat.lang.exception;

/**
 * @author : denglinhai
 * @date : 22:54 2020/2/26
 */
public abstract class AbstractException extends RuntimeException{

    protected String errorMsg;

    public AbstractException(){
        //super(errorMsg);
    }
}
