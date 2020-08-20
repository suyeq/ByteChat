package io.bytechat.tcp.codec;

import cn.hutool.core.lang.Assert;
import io.bytechat.serialize.DefaultSerializerChooser;
import io.bytechat.serialize.Serializer;
import io.bytechat.serialize.SerializerChooser;
import io.bytechat.tcp.entity.DefaultPacket;
import io.bytechat.tcp.entity.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.*;

/**
 * @author : denglinhai
 * @date : 17:25 2020/2/28
 */
public class PacketCodec extends ByteToMessageCodec<Packet> {

    private SerializerChooser serializerChooser;

    public PacketCodec(){
        this.serializerChooser = DefaultSerializerChooser.newInstance();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        if (checkPacket()){
            //检查是否符合packet
        }
        byte algorithm = packet.getAlgorithm();
        Serializer serializer = serializerChooser.choose(algorithm);
        byte[] content = serializer.serialize(packet);
        /**
         * encode
         * packet:
         * |---magic---|---algorithm---|---type---|---length---|---content---|
         * |- 1 byte -|- 1 byte -------|- 1 byte -|- 4 byte ---|-- ? byte ---|
         */
        byteBuf.writeByte(packet.getMagic());
        byteBuf.writeByte(algorithm);
        byteBuf.writeByte(packet.getType());
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int leastPacketLen = 7;
        if (byteBuf.readableBytes() < leastPacketLen){
            return;
        }
        /**
         * 标记读的索引
         * 避免一个packet未读完的情况
         */
        byteBuf.markReaderIndex();
        byte magic = byteBuf.readByte();
        Assert.state(magic == Packet.PACKET_MAGIC, "无效的魔数");
        byte algorithm = byteBuf.readByte();
        byte type = byteBuf.readByte();
        int length = byteBuf.readInt();
        /**
         * 如果可读的字节数没有整个packet的长度
         * 重新设置读索引回到标记点
         */
        if (byteBuf.readableBytes() < length){
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] content = new byte[length];
        byteBuf.readBytes(content);

        Serializer serializer = serializerChooser.choose(algorithm);
        Packet packet = serializer.deserialize(content, DefaultPacket.class);
        list.add(packet);

    }

    /**
     * 检查是否符合packet的定义
     * @return
     */
    private boolean checkPacket() {
        return true;
    }
}
