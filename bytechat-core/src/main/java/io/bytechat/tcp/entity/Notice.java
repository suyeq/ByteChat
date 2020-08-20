package io.bytechat.tcp.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 15:49 2020/7/1
 * 通知实体
 */
@Data
public class Notice {

    /**
     * only ack notice ,no content
     */
    public static byte NOTICE_ACK = (byte) 0 << 1;

    public static byte NOTICE_MSG = (byte) 1 << 2;

    /**
     * notice type
     */
    private byte type;

    /**
     *  content
     */
    private Map<String, Object> content;

    public boolean isOnlyAck(){
        return type == NOTICE_ACK;
    }
}
