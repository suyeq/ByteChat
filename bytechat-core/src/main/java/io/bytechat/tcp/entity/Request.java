package io.bytechat.tcp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 16:46 2020/2/28
 * 请求实体
 */
@Data
@NoArgsConstructor
public class Request {

    /**
     * only ack notice ,no content
     */
    public static byte REQUEST_ACK = (byte) 1 << 0;

    public static byte REQUEST_MSG = (byte) 1 << 1;

    public static byte REQUEST_PING = (byte) 1 << 2;

    private byte type;

    /**
     * 请求服务的服务名称
     */
    private String serviceName;

    /**
     * 方法名，处理请求的方法
     */
    private String methodName;

    /**
     * 传入的参数
     */
    private Map<String, Object> params;

    public boolean isOnlyAck(){
        return type == REQUEST_ACK;
    }

    public boolean isOnlyMsg(){
        return type == REQUEST_MSG;
    }
}
