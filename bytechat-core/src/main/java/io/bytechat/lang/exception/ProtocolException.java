package io.bytechat.lang.exception;

/**
 * @author : denglinhai
 * @date : 14:09 2020/2/28
 */
public class ProtocolException extends BaseException {

    public ProtocolException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
