package io.bytechat.tcp.entity;

import lombok.Data;

/**
 * @author : denglinhai
 * @date : 16:09 2020/2/28
 * tcp上传输协议实体
 */
@Data
public abstract class Packet {

    public static byte PACKET_MAGIC = (byte) 0x1;

    private byte magic = Packet.PACKET_MAGIC;

    private long id;

    /**
     * 1 ----> request
     * 2 ----> response
     * 3 ----> command
     */
    private byte type;

    private Request request;

    private Payload payload;

    private Command command;

    private byte algorithm = algorithm();

    /**
     * 哪种序列化协议
     * @return
     */
    public abstract byte algorithm();

}
