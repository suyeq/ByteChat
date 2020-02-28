package io.bytechat.lang.exception;

/**
 * @author : denglinhai
 * @date : 15:32 2020/2/27
 * 异常枚举类
 */
public enum ExceptionEnum {

    /**
     * 加载配置类空指针
     */
    CONFIG_EXCEPTION_NULL("获取配置类异常,配置类为空"),

    /**
     *session未绑定channel异常
     */
    SESSION_EXCEPTION_NOT_BIND("session尚未绑定channel"),

    /**
     * 协议格式错误
     */
    PROTOCOL_EXCEPTION_WRONG_FORMAT("协议格式错误"),

    /**
     * 未知的协议
     */
    PROTOCOL_EXCEPTION_UNKNOWN("未知的协议");

    private String msg;

    private ExceptionEnum(String msg){
        this.msg = msg;
    }

    @Override
    public String toString(){
        return msg;
    }
}
