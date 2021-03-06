package io.bytechat.tcp.entity;

import lombok.Data;

/**
 * @author : denglinhai
 * @date : 16:09 2020/2/28
 * tcp上传输协议实体
 */
@Data
public abstract class Packet {

    public static byte PACKET_MAGIC = (byte) 1 << 0;

    public static byte PACKET_REQUEST = (byte) 1 << 1;

    public static byte PACKET_RESPONSE = (byte) 1 << 2;

    public static byte PACKET_COMMAND = (byte) 1 << 3;

    public static byte PACKET_NOTICE = (byte) 1 << 4;

    private byte magic = Packet.PACKET_MAGIC;

    private long id;

    /**
     * 1 ----> request
     * 2 ----> response
     * 3 ----> command
     * 4 ----> notice
     */
    private byte type;

    private Request request;

    private Payload payload;

    private Command command;

    private Notice notice;

    private byte algorithm = algorithm();

    private boolean isAsyncHandle = isAsyncHandle();

    /**
     * 哪种序列化协议
     * @return
     */
    public abstract byte algorithm();

    /**
     * 是否采用异步处理
     * @return
     */
    public abstract boolean isAsyncHandle();

}
